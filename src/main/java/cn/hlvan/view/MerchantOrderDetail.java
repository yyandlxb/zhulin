package cn.hlvan.view;

import cn.hlvan.manager.database.tables.records.OrderRecord;
import lombok.Data;

import java.util.List;

@Data
public class MerchantOrderDetail {
    private OrderRecord orderRecord;
    private List<UserOrder> UserOrders;
}
