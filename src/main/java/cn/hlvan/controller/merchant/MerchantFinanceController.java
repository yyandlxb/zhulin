package cn.hlvan.controller.merchant;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.FinanceService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 订单打款给管理员
     */
    @RequestMapping("/make_money")
    public Reply makeMoney(@Authenticated AuthorizedUser user,
                           @RequestJson(value = "orderId") Integer orderId) {
        financeService.makeMoney(user.getId(), orderId);
        return Reply.success();
    }

}
