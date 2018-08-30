package cn.hlvan.controller.merchant;

import cn.hlvan.controller.admin.AdminController;
import cn.hlvan.manager.database.tables.Order;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.merchant.OrderService;
import cn.hlvan.user.controller.AuthorizedUser;
import cn.hlvan.util.Reply;
import org.apache.commons.lang.RandomStringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/merchant")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
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

    /**
     * 添加订单
     */
    @PostMapping("/order/add")
    public Reply addOrder(OrderService.OrderFrom orderFrom,@Authenticated AuthorizedUser user ){
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderCode(System.currentTimeMillis()+ RandomStringUtils.random(3));
        orderRecord.setUserId(user.getId());
        orderRecord.setTotal(orderFrom.getTotal());
        orderRecord.setMerchantPrice(orderFrom.getMerchantPrice());
        orderRecord.setEassyType(orderFrom.getEassyType());
        orderRecord.setNotes(orderFrom.getNotes());
        orderRecord.setOrderTitle(orderFrom.getOrderTitle());
        orderRecord.setOriginalLevel(orderFrom.getOriginalLevel());
        orderRecord.setPicture(orderFrom.getPicture());
        orderRecord.setType(orderFrom.getType());
        orderRecord.setEndTime(orderFrom.getEndTime());
        orderRecord.setRequire(orderFrom.getRequire());
        orderRecord.setEassyTotal(orderFrom.getEassyTotal());
        orderRecord.setWordCount(orderFrom.getWordCount());
        Boolean b = orderService.addOrder(orderRecord);
        if(b){
            return Reply.success();
        }else{
            return Reply.fail().message("订单添加失败");
        }
    }
}
