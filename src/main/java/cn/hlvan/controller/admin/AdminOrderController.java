package cn.hlvan.controller.admin;

import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    public Reply auditing(@RequestParam Integer id, String status, String result, BigDecimal price,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endTime){
        boolean b = orderService.auditing(id,status,result,price,endTime);
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
