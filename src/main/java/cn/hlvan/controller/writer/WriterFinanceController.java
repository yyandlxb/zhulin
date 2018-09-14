package cn.hlvan.controller.writer;

import cn.hlvan.configure.RequestJson;
import cn.hlvan.manager.database.tables.records.ApplyFinanceRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.permission.PermissionEnum;
import cn.hlvan.security.permission.RequirePermission;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.service.FinanceService;
import cn.hlvan.util.Reply;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static cn.hlvan.manager.database.tables.ApplyFinance.APPLY_FINANCE;

@RestController
@RequestMapping("/writer/finance")
@RequirePermission(PermissionEnum.APPLY_FINANCE)
public class WriterFinanceController {

    private FinanceService financeService;

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    private DSLContext dsl;

    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping("/list")
    public Reply applyFinance(@Authenticated AuthorizedUser user, Byte status,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startTime,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime){

        List<Condition> list = new ArrayList<>();
        list.add(APPLY_FINANCE.USER_ID.eq(user.getId()));
        if (null != startTime)
            list.add(APPLY_FINANCE.CREATED_AT.greaterOrEqual(Timestamp.valueOf( LocalDateTime.of(startTime,LocalTime.MIN))));
        if (null != endTime)
            list.add(APPLY_FINANCE.CREATED_AT.lessOrEqual(Timestamp.valueOf( LocalDateTime.of(endTime,LocalTime.MAX))));
        if (null != status)
            list.add(APPLY_FINANCE.STATUS.eq(status));
        List<ApplyFinanceRecord> applyFinanceRecords = dsl.selectFrom(APPLY_FINANCE).where(list).fetch();
        return Reply.success().data(applyFinanceRecords);
    }

    @PostMapping("/create")
    public Reply createApplyFinance(@Authenticated AuthorizedUser user, @RequestJson(value = "money")BigDecimal money){
        financeService.create(user.getId(),money);
        return Reply.success();
    }
}
