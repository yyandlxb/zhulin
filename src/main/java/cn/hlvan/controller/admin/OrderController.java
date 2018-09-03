package cn.hlvan.controller.admin;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.admin.OrderService;
import cn.hlvan.user.controller.AuthorizedUser;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.hlvan.constant.OrderStatus.AWAIT_AUDUTING;
import static cn.hlvan.manager.database.Tables.ORDER;

@RestController("/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(cn.hlvan.controller.merchant.OrderController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;



    /**
     * 查询待审核的订单
     */
    @GetMapping("/list")
    public Reply queryOrder(@Authenticated AuthorizedUser user){
        List<OrderRecord> orderRecords = dsl.selectFrom(ORDER).where(ORDER.ORDER_STATUS.eq(AWAIT_AUDUTING)).fetchInto(OrderRecord.class);
        return Reply.success().data(orderRecords);
    }

    /**
     * 审核订单
     */

    public Reply auditingSuccess(Integer[] ids){
        Integer b = orderService.update(ids);
        return Reply.success().data(b);
    }

}
