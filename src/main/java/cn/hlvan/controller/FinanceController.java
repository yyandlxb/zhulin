package cn.hlvan.controller;

import cn.hlvan.manager.database.tables.records.TradeRecordRecord;
import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.permission.PermissionEnum;
import cn.hlvan.security.permission.RequirePermission;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.util.Reply;
import cn.hlvan.view.UserMoneyRecordView;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static cn.hlvan.manager.database.tables.TradeRecord.TRADE_RECORD;
import static cn.hlvan.manager.database.tables.UserMoney.USER_MONEY;

@RestController
@RequestMapping("/finance/record")
public class FinanceController {

    private DSLContext dsl;

    public void setDsl(org.jooq.DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping("/list")
    @RequirePermission(PermissionEnum.APPLY_FINANCE)
    public Reply financeList(@Authenticated AuthorizedUser user,String startTime,String endTime) {
        UserMoneyRecordView userMoneyRecordView = new UserMoneyRecordView();
        //余额信息
        UserMoneyRecord userMoneyRecord = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(user.getId())).fetchSingle();
        userMoneyRecordView.setUserMoneyRecord(userMoneyRecord);
        List<Condition> list = new ArrayList<>();
        list.add(TRADE_RECORD.USER_ID.eq(user.getId()));
        if (null != startTime)
            list.add(TRADE_RECORD.CREATED_AT.greaterOrEqual(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN))));
        if (null != endTime)
            list.add(TRADE_RECORD.CREATED_AT.lessOrEqual(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX))));

        Integer count = dsl.selectCount().from(TRADE_RECORD).where(list).fetchOne().value1();
        List<TradeRecordRecord> tradeRecordRecords = dsl.selectFrom(TRADE_RECORD).where(list).fetch();
        userMoneyRecordView.setTradeRecords(tradeRecordRecords);
        //收入
        userMoneyRecordView.setIncome(dsl.select(DSL.sum(TRADE_RECORD.MONEY))
                                         .from(TRADE_RECORD).where(list)
                                         .and(TRADE_RECORD.MONEY.greaterOrEqual(new BigDecimal(0)))
                                         .fetchOneInto(BigDecimal.class));
        userMoneyRecordView.setExpend(dsl.select(DSL.sum(TRADE_RECORD.MONEY))
                                         .from(TRADE_RECORD).where(list)
                                         .and(TRADE_RECORD.MONEY.lessOrEqual(new BigDecimal(0)))
                                         .fetchOneInto(BigDecimal.class));
        return Reply.success().data(userMoneyRecordView);
    }
}
