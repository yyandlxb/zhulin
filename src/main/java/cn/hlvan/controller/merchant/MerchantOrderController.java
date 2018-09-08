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
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(orderForm.getId());
        orderRecord.from(orderForm);
        OrderRecord orderR = dsl.select(ORDER.fields()).from(ORDER)
                                .where(ORDER.ID.eq(orderForm.getId()))
                                .and(ORDER.USER_ID.eq(user.getId()))
                                .fetchSingleInto(OrderRecord.class);

        if (null != orderR) {
            Integer count = dsl.select(sum(USER_ORDER.RESERVE_TOTAL)).from(USER_ORDER)
                               .where(USER_ORDER.ORDER_CODE.eq(orderR.getOrderCode()))
                               .and(USER_ORDER.STATUS.eq(Byte.valueOf("1"))).fetchOneInto(Integer.class);
            if (orderForm.getTotal() >= count) {
                boolean b = orderService.update(orderRecord);
                if (b) {
                    return Reply.success();
                } else {
                    return Reply.fail().message("更新订单失败");
                }
            } else {
                return Reply.fail().message("更新订单失败,数量小于已预订数量");
            }
        } else {
            return Reply.fail().message("此状态不能进行修改");
        }
    }

    /**
     * 添加订单
     */
    @PostMapping("/create")
    public Reply addOrder(@RequestBody OrderForm orderFrom, @Authenticated AuthorizedUser user) {

        Boolean b = orderService.addOrder(orderFrom,user.getId());
//        logger.info("订单添加成功" + orderFrom.getOrderCode());
        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("订单添加失败");
        }
    }
}
