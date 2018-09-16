package cn.hlvan.controller.merchant;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.FinanceService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.hlvan.constant.OrderEssayStatus.ACCEPT_SUCCESS;
import static cn.hlvan.constant.OrderStatus.END;
import static cn.hlvan.constant.OrderStatus.MAKE_MONEY;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@RestController("merchantFinanceController")
@RequestMapping("/merchant/finance")
public class MerchantFinanceController {

    private FinanceService financeService;

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }


    //订单要打款的金额展示
    @GetMapping("/make_money/info")
    public Reply makeMoneyInfo(@Authenticated AuthorizedUser user,
                           @RequestJson(value = "orderId") Integer orderId) {
        FinanceService.Money money = dsl.select(DSL.sum(ORDER.MERCHANT_PRICE).as("merchantPrice"), ORDER.ORDER_CODE, ORDER.USER_ID,
            DSL.sum(ORDER.ADMIN_PRICE).as("adminPrice")).from(ORDER).innerJoin(USER_ORDER)
                                        .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE)).innerJoin(ORDER_ESSAY)
                                        .on(USER_ORDER.ID.eq(ORDER_ESSAY.USER_ORDER_ID)).and(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS))
                                        .and(ORDER.ID.eq(orderId)).and(ORDER.ORDER_STATUS.eq(END))
                                        .and(ORDER.USER_ID.eq(user.getId()))
                                        .groupBy(ORDER.ORDER_CODE,ORDER.USER_ID).fetchOneInto(FinanceService.Money.class);
        return Reply.success().data(money);
    }

    /**
     * 订单打款给管理员
     */
    @PostMapping("/make_money")
    public Reply makeMoney(@Authenticated AuthorizedUser user,
                           @RequestJson(value = "orderId") Integer orderId) {
        financeService.makeMoney(user.getId(), orderId);
        return Reply.success();
    }

}
