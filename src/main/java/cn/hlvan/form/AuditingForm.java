package cn.hlvan.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuditingForm {
    private Integer id;
    private Byte status;
    private String result;
    private BigDecimal price;
    private String endTime;
}
