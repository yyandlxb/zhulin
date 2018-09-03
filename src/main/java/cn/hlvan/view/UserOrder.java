package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserOrder {
    private Integer userOrderId;//用户订单Id
    private String essayTitle;//文章标题
    private BigDecimal adminPrice;//价格
    private String essayType;//文章领域
    private String wordCount;//文章大约字数
    private Integer pictureTotal;//图片数量
    private Double originalLevel;//原创度
}
