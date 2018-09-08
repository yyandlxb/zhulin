package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.form.AuditingForm;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        boolean b = orderService.auditing(auditingForm.getId(),auditingForm.getStatus(),auditingForm.getStatus(),
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
}
