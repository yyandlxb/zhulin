package cn.hlvan.service;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.manager.database.tables.records.LimitTimeRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.manager.database.tables.records.UserOrderRecord;
import cn.hlvan.security.AuthorizedUser;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static cn.hlvan.constant.OrderStatus.PUBLICING;
import static cn.hlvan.constant.WriterOrderStatus.APPOINT_SUCCESS;
import static cn.hlvan.constant.WriterOrderStatus.WAIT_APPOINT;
import static cn.hlvan.manager.database.tables.LimitTime.LIMIT_TIME;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private DSLContext dsl;
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public Boolean addOrder(OrderRecord orderRecord) {

        return dsl.executeInsert(orderRecord) > 0;
    }

    public List<Condition> buildConditions(OrderQueryForm orderForm) {
        List<Condition> list = new ArrayList<>();
        String startTime = orderForm.getStartTime();
        if (StringUtils.isNotBlank(startTime)) {
            LocalDateTime start = LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN);
            list.add(ORDER.CREATED_AT.greaterOrEqual(Timestamp.valueOf(start)));
        }

        String endTime = orderForm.getEndTime();
        if (StringUtils.isNotBlank(startTime)) {
            LocalDateTime end = LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX);
            list.add(ORDER.CREATED_AT.lessOrEqual(Timestamp.valueOf(end)));
        }
        Byte status = orderForm.getStatus();
        if (null != status) {
            list.add(ORDER.ORDER_STATUS.eq(status));
        }
        String orderCode = orderForm.getOrderCode();
        if (StringUtils.isNotBlank(orderCode)) {
            list.add(ORDER.ORDER_CODE.contains(orderCode));
        }
        return list;
    }

    public boolean update(OrderRecord orderRecord) {
        return dsl.executeUpdate(orderRecord) > 0;
    }

    public Integer delete(Integer[] ids, Integer userId) {
        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, OrderStatus.DELETE)
                  .where(ORDER.ORDER_STATUS.eq(OrderStatus.WAIT_AUDITING))
                  .and(ORDER.ID.in(ids))
                  .and(ORDER.USER_ID.eq(userId))
                  .execute();
    }

    public boolean auditing(Integer id, String status, String result, BigDecimal price) {

        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, Byte.valueOf(status))
                  .set(ORDER.RESULT, result).set(ORDER.ADMIN_PRICE, price)
                  .where(ORDER.ID.eq(id)).execute() > 0;
    }

    @Transactional
    public boolean distribute(String orderId, Integer reserveTotal, Integer userId) {
        //查询订单还有多少文章
        OrderRecord orderRecord = dsl.select(ORDER.fields()).from(ORDER).where(ORDER.ORDER_CODE.eq(orderId)).fetchSingleInto(OrderRecord.class);
        if (orderRecord.getTotal() >= reserveTotal){
            UserOrderRecord userOrderRecord = new UserOrderRecord();
            userOrderRecord.setOrderCode(orderRecord.getOrderCode());
            userOrderRecord.setUserId(userId);
            userOrderRecord.setReserveTotal(reserveTotal);
            userOrderRecord.setStatus(WAIT_APPOINT);
            //减少订单中的文章数量
            dsl.update(ORDER).set(ORDER.TOTAL,orderRecord.getTotal() - reserveTotal)
                    .set(ORDER.APPOINT_TOTAL,orderRecord.getAppointTotal() + reserveTotal)
               .where(ORDER.ORDER_CODE.eq(orderRecord.getOrderCode()))
               .execute();
            //邮件通知
            String s = dsl.select(USER.EMAIL).from(USER).where(USER.ID.eq(userId)).fetchOneInto(String.class);
            if (StringUtils.isNotBlank(s)){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromMail);
                message.setTo(s);
                message.setSubject("管理员分配任务");
                message.setText("管理员分配了文章任务给您，请注意查收，订单号为："+orderRecord.getOrderCode()
                                +"系统邮件，请勿回复");
                javaMailSender.send(message);
            }
            return dsl.executeInsert(userOrderRecord) > 0;
        }else {
            return false;
        }
    }

    @Transactional
    public boolean addAppoint(Integer orderId, Integer total, AuthorizedUser user) {
        OrderRecord orderRecords = dsl.selectFrom(ORDER)
                .where(ORDER.ID.eq(orderId))
                .and(ORDER.ORDER_STATUS.eq(PUBLICING))
                .fetchSingleInto(OrderRecord.class);
        //构造预约订单信息
        UserOrderRecord userOrderRecord = new UserOrderRecord();
        //查询是否已经预约过了
        UserOrderRecord userOrder = dsl.selectFrom(USER_ORDER)
                .where(USER_ORDER.ORDER_CODE.eq(orderRecords.getOrderCode()))
                .and(USER_ORDER.USER_ID.eq(user.getId())).fetchOneInto(UserOrderRecord.class);

        Integer min = orderRecords.getTotal() -  orderRecords.getAppointTotal();
        if (null != userOrder && orderRecords.getTotal() >= total ){

            dsl.update(USER_ORDER).set(USER_ORDER.RESERVE_TOTAL,userOrder.getReserveTotal() + total)
                    .where(USER_ORDER.ORDER_CODE.eq(userOrder.getOrderCode())).execute();
            //减少订单中的文章数量
            dsl.update(ORDER).set(ORDER.TOTAL,orderRecords.getTotal() - total)
                    .set(ORDER.APPOINT_TOTAL,orderRecords.getAppointTotal() + total)
                    .where(ORDER.ORDER_CODE.eq(orderRecords.getOrderCode()))
                    .execute();
        }
        if (null == userOrder && orderRecords.getTotal() >= total ){
            userOrderRecord.setOrderCode(orderRecords.getOrderCode());
            userOrderRecord.setReserveTotal(total);
            userOrderRecord.setUserId(user.getId());
            userOrderRecord.setStatus(APPOINT_SUCCESS);
            dsl.executeInsert(userOrderRecord);
            //减少订单中的文章数量
            dsl.update(ORDER).set(ORDER.TOTAL,orderRecords.getTotal() - total)
                    .set(ORDER.APPOINT_TOTAL,orderRecords.getAppointTotal() + total)
                    .where(ORDER.ORDER_CODE.eq(orderRecords.getOrderCode()))
                    .execute();
            return true;
        }
        return false;
    }

    public boolean deleteAppoint(Integer userOrderId, Integer total, AuthorizedUser user) {
        List<LimitTimeRecord> limitTimeRecords = dsl.selectFrom(LIMIT_TIME).fetchInto(LimitTimeRecord.class);
        if (null != limitTimeRecords && limitTimeRecords.size() >0){
            //判断是否已经超过了截止时间
            LimitTimeRecord limitTimeRecord = limitTimeRecords.get(0);

        }

        return true;
    }

    @Data
    public class OrderForm {
        @NotNull
        private Integer total;//文章数量
        private BigDecimal merchantPrice;//商户定价
        @NotNull
        private String essayType;//文章领域
        private String notes;//备注
        @NotNull
        private String orderTitle;//订单标题
        private Double originalLevel;//原创度要求
        private Integer picture;//图片数量要求
        private Byte type;//类型 0-流量文，1-养号文
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endTime;//截止交稿时间
        private String require;//要求
        private String wordCount;//字数要求

    }

    @Data
    public class OrderQueryForm {
        private Integer id;
        private String startTime;
        private String endTime;

        private String orderCode;
        private Byte status;

    }

    //写手确定预约

    @Transactional
    public boolean appoint(Integer id,Integer userId){
       return dsl.update(USER_ORDER).set(USER_ORDER.STATUS,APPOINT_SUCCESS)
               .where(USER_ORDER.ID.eq(id)).and(USER_ORDER.USER_ID.eq(userId)).execute() > 0 ;
    }
}
