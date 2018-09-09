package cn.hlvan.controller.writer;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Page;
import cn.hlvan.util.Reply;
import cn.hlvan.view.MerchantOrderDetail;
import cn.hlvan.view.UserOrder;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@RestController("writerOrderController")
@RequestMapping("/writer/order")
public class WriterOrderController {
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

    @GetMapping("/detail")
    public Reply list(@RequestParam Integer id) {
        OrderRecord orderRecord = dsl.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchSingleInto(OrderRecord.class);
        return Reply.success().data(orderRecord);
    }
    //预约列表
    @GetMapping("/appoint/list")
    public Reply appointmentList(@Authenticated AuthorizedUser user, Pageable pageable) {

        Integer count = dsl.selectCount().from(USER_ORDER).innerJoin(USER_ORDER)
                           .on(USER_ORDER.ORDER_CODE.eq(ORDER.ORDER_CODE))
                           .and(USER_ORDER.USER_ID.eq(user.getId())).fetchOne().value1();
        List<UserOrder> userOrders;
        if (pageable.getOffset() > count) {
            userOrders = Collections.emptyList();
        } else {

            userOrders = dsl.select(ORDER.ADMIN_PRICE, ORDER.APPOINT_TOTAL, ORDER.ORDER_TITLE, ORDER.ORIGINAL_LEVEL,
                ORDER.EASSY_TYPE)
                            .select(USER_ORDER.RESERVE_TOTAL, USER_ORDER.COMPLETE, USER_ORDER.STATUS.as("userOrderStatus")
                                , USER_ORDER.ID.as("userOrderId"))
                            .from(USER_ORDER).innerJoin(USER_ORDER)
                            .on(USER_ORDER.ORDER_CODE.eq(ORDER.ORDER_CODE))
                            .and(USER_ORDER.USER_ID.eq(user.getId()))
                            .orderBy(USER_ORDER.STATUS.desc())
                            .limit((int) pageable.getOffset(), pageable.getPageSize())
                            .fetchInto(UserOrder.class);
        }
        return Reply.success().data(new Page<>(userOrders, pageable, count));

    }

    //确定预约
    @PostMapping("/appoint")
    public Reply sureAppoint(@RequestJson(value = "userOrderId")Integer userOrderId, @Authenticated AuthorizedUser user) {

        boolean appoint = orderService.appoint(userOrderId, user.getId());
        if (appoint) {
            return Reply.success();
        } else {
            return Reply.fail().message("预约失败");
        }
    }

    //添加预约
    @PostMapping("/create")
    public Reply writerAppointAdd(@RequestJson(value = "orderId") Integer orderId,@RequestJson(value = "total") Integer total,
                                  @Authenticated AuthorizedUser user) {

        boolean b = orderService.addAppoint(orderId, total, user);
        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("预约失败");
        }
    }

    //手动取消预约
    @PostMapping("/delete")
    public Reply writerAppointDelete(@RequestJson(value = "userOrderId") Integer userOrderId,
                                     @RequestJson(value = "total") Integer total,
                                     @Authenticated AuthorizedUser user) {
        orderService.deleteAppoint(userOrderId, total, user);
        return Reply.success();
    }

}
