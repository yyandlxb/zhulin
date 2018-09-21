package cn.hlvan.service;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.exception.ApplicationException;
import cn.hlvan.form.AuditingEssayForm;
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

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hlvan.constant.OrderStatus.PUBLICING;
import static cn.hlvan.constant.OrderStatus.WAIT_AUDITING;
import static cn.hlvan.constant.WriterOrderStatus.APPOINT_SUCCESS;
import static cn.hlvan.constant.WriterOrderStatus.WAIT_APPOINT;
import static cn.hlvan.manager.database.tables.LimitTime.LIMIT_TIME;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
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
                  .where(ORDER.ORDER_STATUS.eq(WAIT_AUDITING))
                  .and(ORDER.ID.in(ids))
                  .and(ORDER.USER_ID.eq(userId))
                  .execute();
    }

    public boolean auditing(Integer id, Byte status, String result, BigDecimal price, LocalDate endTime) {

        LocalDateTime of = LocalDateTime.of(endTime, LocalTime.MIN);
        return dsl.update(ORDER).set(ORDER.ORDER_STATUS, status)
                  .set(ORDER.RESULT, result).set(ORDER.ADMIN_PRICE, price)
                  .set(ORDER.ADMIN_END_TIME, Timestamp.valueOf(of))
                  .where(ORDER.ID.eq(id)).and(ORDER.ORDER_STATUS.eq(WAIT_AUDITING)).execute() > 0;
    }

    @Transactional
    public boolean distribute(Integer orderId, Integer reserveTotal, Integer userId) {
        //查询订单还有多少文章
        OrderRecord orderRecord = dsl.select(ORDER.fields()).from(ORDER)
                                     .where(ORDER.ID.eq(orderId)).fetchSingleInto(OrderRecord.class);
        if (orderRecord.getTotal() >= reserveTotal) {
            UserOrderRecord userOrderRecord = new UserOrderRecord();
            userOrderRecord.setOrderCode(orderRecord.getOrderCode());
            userOrderRecord.setUserId(userId);
            userOrderRecord.setReserveTotal(reserveTotal);
            userOrderRecord.setStatus(WAIT_APPOINT);
            //减少订单中的文章数量
            dsl.update(ORDER).set(ORDER.TOTAL, orderRecord.getTotal() - reserveTotal)
               .set(ORDER.APPOINT_TOTAL, orderRecord.getAppointTotal() + reserveTotal)
               .where(ORDER.ORDER_CODE.eq(orderRecord.getOrderCode()))
               .execute();
            //邮件通知
            String s = dsl.select(USER.EMAIL).from(USER).where(USER.ID.eq(userId)).fetchOneInto(String.class);
            if (StringUtils.isNotBlank(s)) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromMail);
                message.setTo(s);
                message.setSubject("管理员分配任务");
                message.setText("管理员分配了"+reserveTotal+"篇文章任务给您，请注意查收，订单号为：" + orderRecord.getOrderCode()
                                + "系统邮件，请勿回复");
                javaMailSender.send(message);
            }else{
                throw new ApplicationException("分配失败，分配的用户没有邮箱");
            }
            return dsl.executeInsert(userOrderRecord) > 0;
        } else {
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
        if (null != orderRecords && orderRecords.getTotal() >= total) {
            userOrderRecord.setOrderCode(orderRecords.getOrderCode());
            userOrderRecord.setReserveTotal(total);
            userOrderRecord.setUserId(user.getId());
            userOrderRecord.setStatus(APPOINT_SUCCESS);
            dsl.executeInsert(userOrderRecord);
            //减少订单中的文章数量
            dsl.update(ORDER).set(ORDER.TOTAL, orderRecords.getTotal() - total)
               .set(ORDER.APPOINT_TOTAL, orderRecords.getAppointTotal() + total)
               .where(ORDER.ORDER_CODE.eq(orderRecords.getOrderCode()))
               .execute();
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteAppoint(Integer userOrderId, Integer total, AuthorizedUser user) {
        List<LimitTimeRecord> limitTimeRecords = dsl.selectFrom(LIMIT_TIME).fetchInto(LimitTimeRecord.class);
        if (null != limitTimeRecords && limitTimeRecords.size() > 0) {
            //判断是否已经超过了截止时间
            Integer hours = limitTimeRecords.get(0).getLimitTime();
            UserOrder orderRecord = dsl.select(ORDER.ADMIN_END_TIME,ORDER.TOTAL,ORDER.ORDER_CODE)
                                       .select(USER_ORDER.COMPLETE, USER_ORDER.RESERVE_TOTAL)
                                       .from(USER_ORDER).innerJoin(ORDER)
                                       .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE))
                                       .and(USER_ORDER.ID.eq(userOrderId)).forUpdate().fetchSingleInto(UserOrder.class);
            int i = LocalDateTime.now().plusHours(hours)
                                 .compareTo(orderRecord.getAdminEndTime().toLocalDateTime());

            if (orderRecord.getReserveTotal() >= total && total <= (orderRecord.getReserveTotal() - orderRecord.getComplete())) {
                //超过了截稿时间
                if (i > 0) {
                    //查询用户
                    Integer level = dsl.select(USER.CREDIT_LEVEL)
                                       .from(USER).where(USER.ID.eq(user.getId()))
                                       .fetchOneInto(Integer.class);
                    dsl.update(USER).set(USER.CREDIT_LEVEL, level - 1).where(USER.ID.eq(user.getId())).execute();
                }
                dsl.update(USER_ORDER).set(USER_ORDER.RESERVE_TOTAL, orderRecord.getReserveTotal() - total)
                   .where(USER_ORDER.ID.eq(userOrderId)).execute();
                //跟新订单数量
                dsl.update(ORDER).set(ORDER.TOTAL,orderRecord.getTotal() + total)
                   .where(ORDER.ORDER_CODE.eq(orderRecord.getOrderCode()).and(ORDER.ORDER_STATUS.eq(PUBLICING))).execute();

            } else {
                throw new ApplicationException("取消失败，取消的数量大于预约数量");
            }

        }

    }

    //文章审核接口
    @Transactional
    public boolean auditingEssay(AuditingEssayForm auditingEssayForm) {

        Map<Object,Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(auditingEssayForm.getResult()))
            map.put(ORDER_ESSAY.RESULT, auditingEssayForm.getResult());
        if (null != auditingEssayForm.getOriginalLevel())
            map.put(ORDER_ESSAY.ORIGINAL_LEVEL, auditingEssayForm.getOriginalLevel());
        return dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.STATUS, auditingEssayForm.getStatus())
                  .set(map)
                  .where(ORDER_ESSAY.ID.eq(auditingEssayForm.getId())).execute() > 0;

    }

    public boolean urgeMail(Integer id, Integer userId) {
        //查询未完成订单的用户信息
        List<UserOrder> userOrders = dsl.select(USER_ORDER.fields())
                                        .select(USER.EMAIL,USER.PID)
                                        .from(ORDER).innerJoin(USER_ORDER)
                                        .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE)).innerJoin(USER)
                                        .on(USER_ORDER.USER_ID.eq(USER.ID))
                                        .and(ORDER.ID.eq(id))
                                        .and(ORDER.USER_ID.eq(userId))
                                        .and(USER_ORDER.RESERVE_TOTAL.greaterThan(USER_ORDER.COMPLETE))
                                        .fetchInto(UserOrder.class);

        if (userOrders != null && userOrders.size()>0){

            //查询管理员信息
            String s = dsl.select(USER.EMAIL).from(USER)
                          .where(USER.ID.eq(userOrders.get(0).getPid())).fetchOneInto(String.class);
            //发送邮件
            for (UserOrder u : userOrders) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromMail);
                message.setTo(u.getEmail());
                message.setBcc(s);
                message.setSubject("催稿通知");
                message.setText("你有订单未完成，订单号为：" + u.getOrderCode()
                                + "系统邮件，请勿回复");
                javaMailSender.send(message);
            }
        }else {
            throw new ApplicationException("没有要催稿的用户");
        }
        return true;
    }

    public List<Condition> buildAppointConditions(OrderQueryForm form) {
        List<Condition> list = new ArrayList<>();
        String startTime = form.getStartTime();
        if (StringUtils.isNotBlank(startTime)) {
            LocalDateTime start = LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN);
            list.add(USER_ORDER.CREATED_AT.greaterOrEqual(Timestamp.valueOf(start)));
        }

        String endTime = form.getEndTime();
        if (StringUtils.isNotBlank(startTime)) {
            LocalDateTime end = LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX);
            list.add(USER_ORDER.CREATED_AT.lessOrEqual(Timestamp.valueOf(end)));
        }
        Byte status = form.getStatus();
        if (null != status) {
            list.add(USER_ORDER.STATUS.eq(status));
        }
        String orderCode = form.getOrderCode();
        if (StringUtils.isNotBlank(orderCode)) {
            list.add(USER_ORDER.ORDER_CODE.contains(orderCode));
        }
        return list;

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
    public boolean appoint(Integer id, Integer userId) {
        return dsl.update(USER_ORDER).set(USER_ORDER.STATUS, APPOINT_SUCCESS)
                  .where(USER_ORDER.ID.eq(id)).and(USER_ORDER.USER_ID.eq(userId)).execute() > 0;
    }

    @Data
    public class DriverForm {
        private Integer merchantId;
        @NotBlank
        private String userName;
        @NotBlank
        private String userPhone;
        @NotBlank
        private String plateNumber;
    }
}
