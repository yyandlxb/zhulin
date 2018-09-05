package cn.hlvan.controller;

import cn.hlvan.constant.OrderStatus;
import cn.hlvan.constant.UserType;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import cn.hlvan.view.MerchantOrderDetail;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.hlvan.constant.OrderEssayStatus.*;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@RestController("publicOrderController")
@RequestMapping("/merchant/order")
public class PublicOrderCotroller {
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public Reply list(OrderService.OrderQueryForm orderForm, @Authenticated AuthorizedUser user, Pageable pageable) {

        List<Condition> conditions = orderService.buildConditions(orderForm);
        Integer type = user.getType();
        if (type.equals(UserType.WRITER)) {
            orderForm.setStatus(OrderStatus.PUBLICING);
            conditions.add(USER.PID.eq(user.getPid()));
        } else if (type.equals(UserType.MANAGER)) {
            conditions.add(USER.PID.eq(user.getId()));
        } else {
            conditions.add(ORDER.USER_ID.eq(user.getId()));
        }
        int count = dsl.selectCount().from(ORDER).where(conditions).fetchOne().value1();
        List<OrderRecord> orderRecords;
        if (pageable.getOffset() >= count) {
            orderRecords = Collections.emptyList();
        } else {
            orderRecords = dsl.select(ORDER.fields()).from(ORDER)
                              .innerJoin(USER)
                              .on(USER.ID.eq(ORDER.USER_ID))
                              .where(conditions)
                              .orderBy(ORDER.ID.desc())
                              .limit((int) pageable.getOffset(), pageable.getPageSize())
                              .fetchInto(OrderRecord.class);
        }
        return Reply.success().data(new Page<>(orderRecords, pageable, count));
    }

    @GetMapping("/detail")
    public Reply list(@RequestParam Integer id, @Authenticated AuthorizedUser user) {
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        OrderRecord orderRecord = dsl.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchSingleInto(OrderRecord.class);
        merchantOrderDetail.setOrderRecord(orderRecord);
        List<Condition> conditions = new ArrayList<>();
        Integer type = user.getType();
        if (type.equals(UserType.MERCHANT)) {
            conditions.add(ORDER_ESSAY.STATUS.eq(MERCHANT_WAIT_AUDITING).or(ORDER_ESSAY.STATUS.eq(MERCHANT_REJECTION))
            .or(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS)).or(ORDER_ESSAY.STATUS.eq(ALREADY_PAY)));
        }
        List<OrderEssayRecord> OrderEssayRecords = dsl.select(ORDER_ESSAY.fields())
                                                      .from(USER_ORDER)
                                                      .innerJoin(ORDER_ESSAY)
                                                      .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
                                                      .where(conditions)
                                                      .and(USER_ORDER.ORDER_CODE.eq(orderRecord.getOrderCode()))
                                                      .orderBy(USER_ORDER.UPDATED_AT.desc())
                                                      .fetchInto(OrderEssayRecord.class);
        merchantOrderDetail.setOrderEssayRecords(OrderEssayRecords);
        return Reply.success().data(merchantOrderDetail);
    }

    //创建文章

}