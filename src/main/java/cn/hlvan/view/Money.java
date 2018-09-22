package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Money {
    BigDecimal merchantPrice;
    BigDecimal adminPrice;
    String orderCode;
    Integer userId;
}
