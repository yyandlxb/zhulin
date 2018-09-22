package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UserOrderFinance {
    Integer userId;
    BigDecimal adminPrice;
    String orderCode;
}
