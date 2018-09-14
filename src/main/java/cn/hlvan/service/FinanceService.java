package cn.hlvan.service;

import cn.hlvan.exception.ApplicationException;
import cn.hlvan.manager.database.tables.records.ApplyFinanceRecord;
import cn.hlvan.manager.database.tables.records.TradeRecordRecord;
import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import cn.hlvan.security.AuthorizedUser;
import lombok.Data;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static cn.hlvan.constant.OrderEssayStatus.ACCEPT_SUCCESS;
import static cn.hlvan.constant.OrderStatus.CARRY_OUT;
import static cn.hlvan.constant.OrderStatus.MAKE_MONEY;
import static cn.hlvan.manager.database.tables.ApplyFinance.APPLY_FINANCE;
import static cn.hlvan.manager.database.tables.Order.ORDER;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.User.USER;
import static cn.hlvan.manager.database.tables.UserMoney.USER_MONEY;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@Service
public class FinanceService {
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public void determineMakeMoney(AuthorizedUser user, Integer orderId) {

        boolean present = dsl.selectOne().from(ORDER).where(ORDER.ID.eq(orderId).and(ORDER.ORDER_STATUS.eq(MAKE_MONEY))).fetchOptional().isPresent();
        if (!present) {
            throw new ApplicationException("订单不存在，或订单状态错误");
        }

        String s = UUID.randomUUID().toString();
        Money money = dsl.select(DSL.sum(ORDER.MERCHANT_PRICE).as("merchantPrice"), ORDER.ORDER_CODE, ORDER.USER_ID,
            DSL.sum(ORDER.ADMIN_PRICE).as("adminPrice")).from(ORDER).innerJoin(USER_ORDER)
                         .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE)).innerJoin(ORDER_ESSAY)
                         .on(USER_ORDER.ID.eq(ORDER_ESSAY.USER_ORDER_ID)).and(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS))
                         .and(ORDER.ID.eq(orderId)).and(ORDER.ORDER_STATUS.eq(MAKE_MONEY))
                         .groupBy(ORDER.ORDER_CODE).fetchOneInto(Money.class);
        if (null != money && money.getOrderCode() != null) {
            //记录打款
            TradeRecordRecord tradeRecordRecord = new TradeRecordRecord();
            tradeRecordRecord.setMoney(new BigDecimal(0).subtract(money.getMerchantPrice()));
            tradeRecordRecord.setTradeInfo("打款给管理员,订单号为：" + money.getOrderCode());
            tradeRecordRecord.setTradeCode(s);
            tradeRecordRecord.setUserId(money.getUserId());
            dsl.executeInsert(tradeRecordRecord);

            //管理员与写手增加打款记录
            TradeRecordRecord trr = new TradeRecordRecord();
            trr.setMoney(money.getMerchantPrice());
            trr.setTradeInfo("商家" + user.getAccount() + "打款,订单号为：" + money.getOrderCode());
            trr.setTradeCode(s);
            trr.setUserId(user.getId());
            dsl.executeInsert(trr);

            UserMoneyRecord adminMoney = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(user.getId())).fetchSingle();
            dsl.update(USER_MONEY).set(USER_MONEY.MONEY, adminMoney.getMoney().add(money.getMerchantPrice()))
               .where(USER_MONEY.USER_ID.eq(user.getId())).execute();

            List<UserOrderFinance> userOrderFinances = dsl.select(USER.ID, DSL.sum(ORDER.ADMIN_PRICE).as("userMoney"), ORDER.ORDER_CODE)
                                                          .from(USER).innerJoin(USER_ORDER)
                                                          .on(USER.ID.eq(USER_ORDER.USER_ID)).innerJoin(ORDER_ESSAY)
                                                          .on(USER_ORDER.ID.eq(ORDER_ESSAY.USER_ORDER_ID))
                                                          .and(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS))
                                                          .and(ORDER.ID.eq(orderId)).groupBy(USER.ID, ORDER.ORDER_CODE)
                                                          .fetchInto(UserOrderFinance.class);

            userOrderFinances.forEach(e -> {
                TradeRecordRecord tr = new TradeRecordRecord();
                tr.setMoney(e.getUserMoney());
                tr.setTradeInfo("文章收入，订单号为：" + money.getOrderCode());
                tr.setTradeCode(s);
                tr.setUserId(e.getUserId());
                dsl.executeInsert(tr);
                UserMoneyRecord writerMoney = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(e.getUserId())).fetchSingle();
                dsl.update(USER_MONEY).set(USER_MONEY.MONEY, writerMoney.getMoney().add(e.getUserMoney()))
                   .where(USER_MONEY.USER_ID.eq(e.getUserId())).execute();
            });
        } else {
            throw new ApplicationException("确认打款失败");
        }
        //更新订单状态
        boolean b = dsl.update(ORDER).set(ORDER.ORDER_STATUS, CARRY_OUT).where(ORDER.ID.eq(orderId).and(ORDER.ORDER_STATUS.eq(MAKE_MONEY))).execute() > 0;
        if (!b) {
            throw new ApplicationException("确认打款失败");
        }
    }

    @Transactional
    public void create(Integer id, BigDecimal money) {
        UserMoneyRecord userMoneyRecord = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(id)).fetchSingle();
        if (userMoneyRecord.getMoney().compareTo(money) >= 0) {
            ApplyFinanceRecord applyFinance = new ApplyFinanceRecord();
            applyFinance.setMoney(money);
            applyFinance.setUserId(id);
            dsl.executeInsert(userMoneyRecord);
        } else {
            throw new ApplicationException("输入的金额应小于等于余额");
        }
    }

    public void makeMoney(Integer id, Integer orderId) {
        boolean b = dsl.update(ORDER).set(ORDER.ORDER_STATUS, MAKE_MONEY)
                       .where(ORDER.USER_ID.eq(id).and(ORDER.ID.eq(orderId))).execute() > 0;
        if (!b) {
            throw new ApplicationException("打款失败");
        }
    }

    @Transactional
    public void determineMakeMoneyWriter(AuthorizedUser user, Integer id) {
        String s = UUID.randomUUID().toString();
        ApplyFinanceRecord applyFinanceRecord = dsl.selectFrom(APPLY_FINANCE)
                                                   .where(APPLY_FINANCE.ID.eq(id)
                                                                          .and(APPLY_FINANCE.STATUS.eq(Byte.valueOf("0"))))
                                                   .fetchSingle();

        //查询写手余额
        UserMoney userMoneyRecord = dsl.select(USER.ACCOUNT).select(USER_MONEY.MONEY)

                                       .from(USER_MONEY).innerJoin(USER).on(USER_MONEY.USER_ID.eq(USER.ID))
                                       .and(USER_MONEY.USER_ID.eq(applyFinanceRecord.getUserId())).fetchSingleInto(UserMoney.class);

        //查询管理员余额
        UserMoneyRecord userMoneyAdmin = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(user.getId())).fetchSingle();
        //查询写手信息
        //记录打款
        TradeRecordRecord tradeRecordRecord = new TradeRecordRecord();
        tradeRecordRecord.setMoney(userMoneyRecord.getMoney().subtract(applyFinanceRecord.getMoney()));
        tradeRecordRecord.setTradeInfo("打款给" + userMoneyRecord.getAccount());
        tradeRecordRecord.setTradeCode(s);
        tradeRecordRecord.setUserId(user.getId());
        dsl.executeInsert(tradeRecordRecord);
        //写手增加提现记录
        TradeRecordRecord trr = new TradeRecordRecord();
        trr.setMoney(userMoneyRecord.getMoney().subtract(applyFinanceRecord.getMoney()));
        trr.setTradeInfo("提现");
        trr.setTradeCode(s);
        trr.setUserId(applyFinanceRecord.getUserId());
        dsl.executeInsert(trr);
        //减少余额
        dsl.update(USER_MONEY).set(USER_MONEY.MONEY,userMoneyAdmin.getMoney().subtract(applyFinanceRecord.getMoney()))
           .where(USER_MONEY.USER_ID.eq(user.getId())).execute();
        dsl.update(USER_MONEY).set(USER_MONEY.MONEY,userMoneyRecord.getMoney().subtract(applyFinanceRecord.getMoney()))
           .where(USER_MONEY.USER_ID.eq(applyFinanceRecord.getUserId())).execute();
        //更新打款转态
        dsl.update(APPLY_FINANCE).set(APPLY_FINANCE.STATUS,Byte.valueOf("1")).where(APPLY_FINANCE.ID.eq(id)).execute();

    }

    @Data
    public class Money {
        BigDecimal merchantPrice;
        BigDecimal adminPrice;
        String orderCode;
        Integer userId;
    }

    @Data
    public class UserOrderFinance {
        String orderCode;
        Integer userId;
        BigDecimal userMoney;
    }

    @Data
    public class UserMoney {
        BigDecimal money;
        String account;
        Integer userId;
    }
}
