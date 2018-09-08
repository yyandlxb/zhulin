package cn.hlvan.service;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.exception.ApplicationException;
import cn.hlvan.form.OrderForm;
import cn.hlvan.manager.database.tables.records.LimitTimeRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.manager.database.tables.records.UserOrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.view.UserOrder;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Boolean addOrder(OrderForm orderFrom, Integer id) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderCode(System.currentTimeMillis() + RandomStringUtils.randomNumeric(3));
        orderRecord.setUserId(id);
        orderRecord.setTotal(orderFrom.getTotal());
        orderRecord.setMerchantPrice(orderFrom.getMerchantPrice());
        orderRecord.setEassyType(orderFrom.getEssayType());
        orderRecord.setNotes(orderFrom.getNotes());
        orderRecord.setOrderTitle(orderFrom.getOrderTitle());
        orderRecord.setOriginalLevel(orderFrom.getOriginalLevel());
        orderRecord.setPicture(orderFrom.getPicture());
        orderRecord.setType(orderFrom.getType());
        orderRecord.setEndTime(Timestamp.valueOf(LocalDateTime.of(orderFrom.getEndTime(), LocalTime.MIN)));
        orderRecord.setRequire(orderFrom.getRequire());
        orderRecord.setWordCount(orderFrom.getWordCount());
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

    public Integer delete(Integer ids, Integer userId) {
        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, OrderStatus.DELETE)
                  .where(ORDER.ORDER_STATUS.eq(OrderStatus.WAIT_AUDITING))
                  .and(ORDER.ID.in(ids))
                  .and(ORDER.USER_ID.eq(userId))
                  .execute();
    }

    public boolean auditing(Integer id, String status, String result, BigDecimal price,LocalDateTime endTime) {

        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, Byte.valueOf(status))
                  .set(ORDER.RESULT, result).set(ORDER.ADMIN_PRICE, price)
                  .set(ORDER.ADMIN_END_TIME,Timestamp.valueOf(endTime))
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
        if (null != orderRecords && orderRecords.getTotal() >= total ){
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

    @Transactional
    public void deleteAppoint(Integer userOrderId, Integer total, AuthorizedUser user) {
        List<LimitTimeRecord> limitTimeRecords = dsl.selectFrom(LIMIT_TIME).fetchInto(LimitTimeRecord.class);
        if (null != limitTimeRecords && limitTimeRecords.size() >0){
            //判断是否已经超过了截止时间
            Integer hours = limitTimeRecords.get(0).getLimitTime();
            UserOrder orderRecord = dsl.select(ORDER.ADMIN_END_TIME)
                                         .select(USER_ORDER.COMPLETE,USER_ORDER.RESERVE_TOTAL)
                                         .from(USER_ORDER).innerJoin(ORDER)
                                         .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE))
                                         .and(USER_ORDER.ID.eq(userOrderId)).fetchSingleInto(UserOrder.class);
            int i = LocalDateTime.now().toLocalTime().plusHours(hours)
                                 .compareTo(orderRecord.getAdminEndTime().toLocalDateTime().toLocalTime());

            if (orderRecord.getReserveTotal() >= total && total <= orderRecord.getComplete()){
                //超过了截稿时间
                if (i > 0){
                    //查询用户
                    Integer level = dsl.select(USER.CREDIT_LEVEL)
                                       .from(USER).where(USER.ID.eq(user.getId()))
                                       .fetchOneInto(Integer.class);
                    dsl.update(USER).set(USER.CREDIT_LEVEL,level - 1).where(USER.ID.eq(user.getId())).execute();
                }
                dsl.update(USER_ORDER).set(USER_ORDER.RESERVE_TOTAL,orderRecord.getReserveTotal() - total)
                   .where(USER_ORDER.ID.eq(userOrderId)).execute();

            }else {
                throw new ApplicationException("取消失败，取消的数量大于预约数量");
            }

        }

    }

/*    @Data
    public class OrderForm {

        private Integer total;//文章数量
        private BigDecimal merchantPrice;//商户定价
        @NotBlank
        private String essayType;//文章领域
        private String notes;//备注
        private String orderTitle;//订单标题
        private Double originalLevel;//原创度要求
        private Integer picture;//图片数量要求
        private Byte type;//类型 0-流量文，1-养号文
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endTime;//截止交稿时间
        private String require;//要求
        private String wordCount;//字数要求

    }*/

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
