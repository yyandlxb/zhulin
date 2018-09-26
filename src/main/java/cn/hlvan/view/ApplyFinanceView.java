package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ApplyFinanceView {
    private Timestamp createdAt;
    private BigDecimal money;
    private Byte status;
    private String account;
    private String payPicture;
}
