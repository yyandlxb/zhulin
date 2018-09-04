package cn.hlvan.controller.admin;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;
import static org.jooq.impl.DSL.sum;

@RestController("adminOrderController")
@RequestMapping("/admin/merchant/order")
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
    public Reply auditingOrderList(OrderService.OrderQueryForm orderForm, @Authenticated AuthorizedUser user, Pageable pageable){
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
        }
        return Reply.success().data(new Page<>(orderRecords, pageable, count));
    }
    @GetMapping("/detail")
    public Reply list(@RequestParam Integer id){
        OrderRecord orderRecord = dsl.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchSingleInto(OrderRecord.class);
        return Reply.success().data(orderRecord);
    }
    @PostMapping("/auditing")
    public Reply auditing(@RequestParam Integer id, String status, String result, @RequestParam BigDecimal price){
        boolean b = orderService.auditing(id,status,result,price);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("审核失败");
        }
    }
    @PostMapping("/distribute")
    public Reply distribute(@RequestParam String orderId, Integer appointTotal,Integer userId){
        boolean b = orderService.distribute(orderId,appointTotal,userId);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("分配失败");
        }
    }

}
