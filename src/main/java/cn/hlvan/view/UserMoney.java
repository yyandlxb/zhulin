package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserMoney {
    String account;
    BigDecimal money;
    Integer userId;
}
