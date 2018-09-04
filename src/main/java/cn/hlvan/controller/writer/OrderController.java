package cn.hlvan.controller.writer;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import cn.hlvan.view.UserOrder;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static cn.hlvan.constant.OrderStatus.PUBLICING;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;
import static org.jooq.impl.DSL.sum;

@RestController("writerOrderController")
@RequestMapping("/writer/order")
public class OrderController {
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

    //订单列表
    @GetMapping("/list")
    public Reply receiveOrder(OrderService.OrderQueryForm orderForm, @Authenticated AuthorizedUser user, Pageable pageable) {
        orderForm.setStatus(1);
        List<Condition> conditions = orderService.buildConditions(orderForm);
        conditions.add(USER.PID.eq(user.getId()));
        int count = dsl.selectCount().from(ORDER).where(conditions).fetchOne().value1();
        List<OrderRecord> orderRecords;
        if (pageable.getOffset() >= count) {
            orderRecords = Collections.emptyList();
        } else {
            orderRecords = dsl.select(ORDER.fields()).from(ORDER)
                              .innerJoin(USER).on(ORDER.USER_ID.eq(USER.ID))
                              .where(conditions)
                              .orderBy(ORDER.ID.desc())
                              .limit((int) pageable.getOffset(), pageable.getPageSize())
                              .fetchInto(OrderRecord.class);
            orderRecords.forEach(e -> e.setBespokeTotal(
                dsl.select(sum(USER_ORDER.RESERVE_TOTAL)).from(USER_ORDER)
                   .where(USER_ORDER.ORDER_CODE.eq(e.getOrderCode()).and(USER_ORDER.STATUS.eq(PUBLICING))
                   ).fetchOneInto(Integer.class)));
        }
        return Reply.success().data(new Page<>(orderRecords, pageable, count));
    }

    //预约列表
    @GetMapping("/appoint/list")
    public Reply appointment(@Authenticated AuthorizedUser user, Pageable pageable) {

        Integer count = dsl.selectCount().from(USER_ORDER).innerJoin(USER_ORDER)
                           .on(USER_ORDER.ORDER_CODE.eq(ORDER.ORDER_CODE))
                           .and(USER_ORDER.USER_ID.eq(user.getId())).fetchOne().value1();
        List<UserOrder> userOrders;
        if (pageable.getOffset() > count) {
            userOrders = Collections.emptyList();
        } else {

            userOrders = dsl.select(ORDER.ADMIN_PRICE, ORDER.BESPOKE_TOTAL, ORDER.ORDER_TITLE, ORDER.ORIGINAL_LEVEL, ORDER.EASSY_TYPE)
                            .select(USER_ORDER.RESERVE_TOTAL, USER_ORDER.COMPLETE, USER_ORDER.STATUS.as("userOrderStatus"))
                            .from(USER_ORDER).innerJoin(USER_ORDER)
                            .on(USER_ORDER.ORDER_CODE.eq(ORDER.ORDER_CODE))
                            .and(USER_ORDER.USER_ID.eq(user.getId()))
                            .orderBy(USER_ORDER.STATUS.desc())
                            .limit((int) pageable.getOffset(), pageable.getPageSize())
                            .fetchInto(UserOrder.class);
        }
        return Reply.success().data(new Page<>(userOrders, pageable, count));

    }

}
