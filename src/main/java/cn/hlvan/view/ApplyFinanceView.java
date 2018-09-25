package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ApplyFinanceView {
    private Timestamp createAt;
    private BigDecimal money;
    private Byte status;
    private String account;
}
