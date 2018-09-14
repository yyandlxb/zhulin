package cn.hlvan.view;

import cn.hlvan.manager.database.tables.records.TradeRecordRecord;
import cn.hlvan.manager.database.tables.records.UserMoneyRecord;
import lombok.Data;

import java.util.List;

@Data
public class UserMoneyRecordView {
    UserMoneyRecord userMoneyRecord;
    List<TradeRecordRecord> tradeRecords;
}
