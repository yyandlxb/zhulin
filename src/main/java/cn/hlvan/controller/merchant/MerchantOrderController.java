package cn.hlvan.controller.merchant;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.form.OrderForm;
import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;
import static org.jooq.impl.DSL.boolAnd;
import static org.jooq.impl.DSL.e;
import static org.jooq.impl.DSL.sum;

@RestController("merchantOrderController")
@RequestMapping("/merchant/order")
public class MerchantOrderController {

    private static Logger logger = LoggerFactory.getLogger(MerchantOrderController.class);
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

    @PostMapping("/delete")
    public Reply delete(@RequestJson(value = "id") Integer id, @Authenticated AuthorizedUser user) {
        Integer count = orderService.delete(id, user.getId());
        return Reply.success().data(count);
    }

    @PostMapping("/update")
    public Reply update(@RequestBody OrderForm orderForm , @Authenticated AuthorizedUser user) {
        OrderRecord orderR = dsl.select(ORDER.fields()).from(ORDER)
                                .where(ORDER.ID.eq(orderForm.getId()))
                                .and(ORDER.USER_ID.eq(user.getId()))
                                .fetchSingleInto(OrderRecord.class);
        orderR.from(orderForm);

        if (orderForm.getTotal() >= orderR.getAppointTotal()) {
            boolean b = orderService.update(orderR);
            if (b) {
                return Reply.success();
            } else {
                return Reply.fail().message("更新订单失败");
            }
        } else {
            return Reply.fail().message("更新订单失败,数量小于已预订数量");
        }
    }

    /**
     * 添加订单
     */
    @PostMapping("/create")
    public Reply addOrder(@RequestBody OrderForm orderFrom, @Authenticated AuthorizedUser user) {

        Boolean b = orderService.addOrder(orderFrom,user.getId());
        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("订单添加失败");
        }
    }

    //催稿
    @PostMapping("/urge/email")
    public Reply sendMail(@RequestJson(value = "id") Integer id,@Authenticated AuthorizedUser user){

        boolean b = orderService.urgeMail(id,user.getId());
        if (b){
            return Reply.success();
        }else {
            return Reply.fail().message("催稿失败");
        }
    }


}
