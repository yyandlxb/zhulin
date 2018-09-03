package cn.hlvan.controller.writer;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.user.controller.AuthorizedUser;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;
import static org.jooq.impl.DSL.sum;

@RestController("merchantOrderController")
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
    @GetMapping("/list")
    public Reply receiveOrder(OrderService.OrderQueryForm orderForm, @Authenticated AuthorizedUser user, Pageable pageable){
        orderForm.setStatus(1);
        List<Condition> conditions = orderService.buildConditions(orderForm);
        conditions.add(USER.PID.eq(user.getId()) );
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
                            .where(USER_ORDER.ORDER_CODE.eq(e.getOrderCode()).and(USER_ORDER.STATUS.eq(Byte.valueOf("1")))
                            ).fetchOneInto(Integer.class)));
        }
        return Reply.success().data(new Page<>(orderRecords, pageable, count));
    }

    //预约

}
