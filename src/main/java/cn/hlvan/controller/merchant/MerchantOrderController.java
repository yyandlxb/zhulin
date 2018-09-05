package cn.hlvan.controller.merchant;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.OrderService;
import cn.hlvan.util.Reply;
import org.apache.commons.lang.RandomStringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    public Reply delete(@RequestParam Integer[] ids, @Authenticated AuthorizedUser user) {
        Integer count = orderService.delete(ids, user.getId());
        return Reply.success().data(count);
    }

    @PostMapping("/update")
    public Reply update(OrderService.OrderForm orderForm, @RequestParam Integer id, @Authenticated AuthorizedUser user) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(id);
        orderRecord.from(orderForm);
        OrderRecord orderR = dsl.select(ORDER.fields()).from(ORDER)
                                .where(ORDER.ID.eq(id))
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
    public Reply addOrder(OrderService.OrderForm orderFrom, @Authenticated AuthorizedUser user) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderCode(System.currentTimeMillis() + RandomStringUtils.randomNumeric(3));
        orderRecord.setUserId(user.getId());
        orderRecord.setTotal(orderFrom.getTotal());
        orderRecord.setMerchantPrice(orderFrom.getMerchantPrice());
        orderRecord.setEassyType(orderFrom.getEssayType());
        orderRecord.setNotes(orderFrom.getNotes());
        orderRecord.setOrderTitle(orderFrom.getOrderTitle());
        orderRecord.setOriginalLevel(orderFrom.getOriginalLevel());
        orderRecord.setPicture(orderFrom.getPicture());
        orderRecord.setType(orderFrom.getType());
        orderRecord.setEndTime(Timestamp.valueOf(LocalDateTime.of(orderFrom.getEndTime(), LocalTime.MIN)));
        orderRecord.setRequire(orderFrom.getRequire());
        orderRecord.setWordCount(orderFrom.getWordCount());
        Boolean b = orderService.addOrder(orderRecord);
        logger.info("订单添加成功" + orderRecord.getOrderCode());
        if (b) {
            return Reply.success();
        } else {
            return Reply.fail().message("订单添加失败");
        }
    }
}
