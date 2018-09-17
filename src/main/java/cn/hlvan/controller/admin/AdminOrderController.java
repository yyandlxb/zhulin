package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.form.AuditingForm;
import cn.hlvan.manager.database.tables.records.LimitTimeRecord;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.permission.PermissionEnum;
import cn.hlvan.security.permission.RequirePermission;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import cn.hlvan.view.MerchantOrderDetail;
import cn.hlvan.view.User;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hlvan.constant.UserStatus.AUDUTING_SUCCESS;
import static cn.hlvan.constant.UserType.WRITER;
import static cn.hlvan.manager.database.tables.LimitTime.LIMIT_TIME;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@RestController("adminOrderController")
@RequestMapping("/admin/merchant/order")
public class AdminOrderController {
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
    @PostMapping("/auditing")
    @RequirePermission(PermissionEnum.ORDER)
    public Reply auditing(@RequestBody AuditingForm auditingForm){
        boolean b = orderService.auditing(auditingForm.getId(),auditingForm.getStatus(),auditingForm.getResult(),
            auditingForm.getPrice(),auditingForm.getEndTime());
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("审核失败");
        }
    }
    @PostMapping("/distribute")
    @RequirePermission(PermissionEnum.DISTRIBUTE_ORDER)
    public Reply distribute(@RequestJson(value = "orderId") Integer orderId,
                            @RequestJson(value = "appointTotal") Integer appointTotal,
                            @RequestJson(value = "userId") Integer userId){
        boolean b = orderService.distribute(orderId,appointTotal,userId);
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("分配失败");
        }
    }
    @GetMapping("/detail")
    public Reply list(@RequestParam Integer id, @Authenticated AuthorizedUser user) {
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        OrderRecord orderRecord = dsl.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchSingleInto(OrderRecord.class);
        merchantOrderDetail.setOrderRecord(orderRecord);
        List<OrderEssayRecord> orderEssayRecords = dsl.select(ORDER_ESSAY.fields())
                                                      .from(USER_ORDER)
                                                      .innerJoin(ORDER_ESSAY)
                                                      .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
                                                      .where(USER_ORDER.ORDER_CODE.eq(orderRecord.getOrderCode()))
                                                      .orderBy(USER_ORDER.UPDATED_AT.desc())
                                                      .fetchInto(OrderEssayRecord.class);
        merchantOrderDetail.setOrderEssayRecords(orderEssayRecords);
        return Reply.success().data(merchantOrderDetail);
    }

    //获取分配订单的写手信息
    @GetMapping("/writer_list")
    @RequirePermission(PermissionEnum.DISTRIBUTE_ORDER)
    public Reply getWriter(@Authenticated AuthorizedUser user){

        List<User> users = dsl.selectFrom(USER).where(USER.PID.eq(user.getId()))
                              .and(USER.TYPE.eq(WRITER))
                              .and(USER.STATUS.eq(AUDUTING_SUCCESS)).fetchInto(User.class);
        users.forEach(e -> e.setPassword(null));
        return Reply.success().data(users);
    }

    @PostMapping("/time")
    @Transactional
    public Reply updateTime(Integer time){
        boolean b = dsl.update(LIMIT_TIME).set(LIMIT_TIME.LIMIT_TIME_,time).execute() > 0;
        return b ? Reply.success() : Reply.fail().message("更新失败");
    }

    //获取分配订单的写手信息
    @GetMapping("/writer_list")
    public Reply getLimit(){

        List<LimitTimeRecord> limit = dsl.selectFrom(LIMIT_TIME).fetch();
        return Reply.success().data(limit.get(0));
    }
}
