package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserOrder {
    private Integer userOrderId;//用户订单Id
    private String orderCode;//订单号
    private String orderTitle;//订单标题
    private BigDecimal adminPrice;//价格
    private String essayType;//文章领域
    private Integer reserveTotal;//预定数量
    private String wordCount;//文章字数要求
    private Integer picture;//图片数量
    private Integer  complete;//已完成数量
}
