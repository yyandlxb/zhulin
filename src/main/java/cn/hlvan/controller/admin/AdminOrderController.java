package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.form.AuditingForm;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import cn.hlvan.view.MerchantOrderDetail;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
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
    public Reply distribute(@RequestJson(value = "orderId") String orderId,
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
}
