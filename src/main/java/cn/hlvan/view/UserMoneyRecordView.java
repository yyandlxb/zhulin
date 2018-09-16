package cn.hlvan.view;

import cn.hlvan.manager.database.tables.records.TradeRecordRecord;
import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserMoneyRecordView {
    BigDecimal income;//收入
    BigDecimal expend;//支出
    UserMoneyRecord userMoneyRecord;//余额
    List<TradeRecordRecord> tradeRecords;//交易记录
}
