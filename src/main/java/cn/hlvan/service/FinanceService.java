package cn.hlvan.service;

import cn.hlvan.exception.ApplicationException;
import cn.hlvan.manager.database.tables.ApplyFinance;
import cn.hlvan.manager.database.tables.records.ApplyFinanceRecord;
import cn.hlvan.manager.database.tables.records.TradeRecordRecord;
import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import cn.hlvan.manager.database.tables.records.UserRecord;
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
    public void makeMoney(Integer userId, Integer orderId) {
        String s = UUID.randomUUID().toString();
        Money money = dsl.select(DSL.sum(ORDER.MERCHANT_PRICE).as("merchantPrice"),ORDER.ORDER_CODE,
            DSL.sum(ORDER.ADMIN_PRICE).as("adminPrice")).from(ORDER).innerJoin(USER_ORDER)
                                   .on(ORDER.ORDER_CODE.eq(USER_ORDER.ORDER_CODE)).innerJoin(ORDER_ESSAY)
                                   .on(USER_ORDER.ID.eq(ORDER_ESSAY.USER_ORDER_ID)).and(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS))
                                   .and(ORDER.ID.eq(orderId)).and(ORDER.USER_ID.eq(userId))
                         .groupBy(ORDER.ORDER_CODE).fetchOneInto(Money.class);
        if (null != money && money.getOrderCode() != null){
            //记录打款
            TradeRecordRecord tradeRecordRecord = new TradeRecordRecord();
            tradeRecordRecord.setMoney(new BigDecimal(0).divide(money.getMerchantPrice()));
            tradeRecordRecord.setTradeInfo("打款给管理员,订单号为：" + money.getOrderCode());
            tradeRecordRecord.setTradeCode(s);
            tradeRecordRecord.setUserId(userId);
            dsl.executeInsert(tradeRecordRecord);

            //管理员与写手增加打款记录
            UserRecord user = dsl.selectFrom(USER).where(USER.ID.eq(userId)).fetchSingle();
            TradeRecordRecord trr = new TradeRecordRecord();
            trr.setMoney(money.getMerchantPrice());
            trr.setTradeInfo("商家"+user.getAccount()+"打款,订单号为：" + money.getOrderCode());
            trr.setTradeCode(s);
            trr.setUserId(user.getPid());
            dsl.executeInsert(trr);

            UserMoneyRecord adminMoney = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(user.getPid())).fetchSingle();
            dsl.update(USER_MONEY).set(USER_MONEY.MONEY,adminMoney.getMoney().add(money.getMerchantPrice()))
               .where(USER_MONEY.USER_ID.eq(user.getPid())).execute();

            List<UserOrderFinance> userOrderFinances = dsl.select(USER.ID, DSL.sum(ORDER.ADMIN_PRICE).as("userMoney"),ORDER.ORDER_CODE)
                                                          .from(USER).innerJoin(USER_ORDER)
                                                          .on(USER.ID.eq(USER_ORDER.USER_ID)).innerJoin(ORDER_ESSAY)
                                                          .on(USER_ORDER.ID.eq(ORDER_ESSAY.USER_ORDER_ID))
                                                          .and(ORDER_ESSAY.STATUS.eq(ACCEPT_SUCCESS))
                                                          .and(ORDER.ID.eq(orderId)).groupBy(USER.ID,ORDER.ORDER_CODE)
                                                          .fetchInto(UserOrderFinance.class);

            userOrderFinances.forEach(e ->{
                TradeRecordRecord tr = new TradeRecordRecord();
                tr.setMoney(e.getUserMoney());
                tr.setTradeInfo("文章收入，订单号为：" + money.getOrderCode());
                tr.setTradeCode(s);
                tr.setUserId(e.getUserId());
                dsl.executeInsert(tr);
                UserMoneyRecord writerMoney = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(e.getUserId())).fetchSingle();
                dsl.update(USER_MONEY).set(USER_MONEY.MONEY,writerMoney.getMoney().add(e.getUserMoney()))
                   .where(USER_MONEY.USER_ID.eq(e.getUserId())).execute();
            });
        }else {
            throw new ApplicationException("打款失败");
        }

    }

    @Transactional
    public void create(Integer id, BigDecimal money) {
        UserMoneyRecord userMoneyRecord = dsl.selectFrom(USER_MONEY).where(USER_MONEY.USER_ID.eq(id)).fetchSingle();
        if (userMoneyRecord.getMoney().compareTo(money) >= 0){
            ApplyFinanceRecord applyFinance = new ApplyFinanceRecord();
            applyFinance.setMoney(money);
            applyFinance.setUserId(id);
            dsl.executeInsert(userMoneyRecord);
        }else {
            throw new ApplicationException("输入的金额应小于等于余额");
        }
    }

    @Data
    public class Money{
        BigDecimal merchantPrice;
        BigDecimal adminPrice;
        String orderCode;
    }

    @Data
    public class UserOrderFinance{
        String orderCode;
        Integer userId;
        BigDecimal userMoney;
    }
}
