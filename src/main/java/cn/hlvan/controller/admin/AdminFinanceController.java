package cn.hlvan.controller.admin;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.permission.PermissionEnum;
import cn.hlvan.security.permission.RequirePermission;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.FinanceService;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/finance")
public class AdminFinanceController {
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

    @PostMapping("/determine")
    public Reply checkMakeMoney(@Authenticated AuthorizedUser user,
                                @RequestJson(value = "orderId") Integer orderId) {
        financeService.determineMakeMoney(user, orderId);
        return Reply.success();
    }

    @PostMapping("/make_money/writer")
    @RequirePermission(PermissionEnum.APPLY_FINANCE)
    public Reply makeMoneyWriter(@Authenticated AuthorizedUser user,
                                 @RequestJson(value = "id") Integer id) {
        financeService.determineMakeMoneyWriter(user, id);
        return Reply.success();
    }
}
