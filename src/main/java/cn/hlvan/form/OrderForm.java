package cn.hlvan.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderForm {
    private Integer id;
    private Integer total;//文章数量
    private BigDecimal merchantPrice;//商户定价
    @NotBlank
    private String essayType;//文章领域
    private String notes;//备注
    private String orderTitle;//订单标题
    private Double originalLevel;//原创度要求
    private Integer picture;//图片数量要求
    private Byte type;//类型 0-流量文，1-养号文
    private String endTime;//截止交稿时间
    private String require;//要求
    private String wordCount;//字数要求
}
