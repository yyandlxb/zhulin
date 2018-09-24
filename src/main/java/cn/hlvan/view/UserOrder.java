package cn.hlvan.view;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UserOrder {
    private Integer userOrderId;//用户订单Id
    private String essayTitle;//文章标题
    private String orderTitle;//订单标题
    private BigDecimal adminPrice;//价格
    private String essayType;//文章领域
    private String wordCount;//文章大约字数
    private Integer pictureTotal;//图片数量
    private Double originalLevel;//原创
    private Integer complete;//已完成数量
    private Integer reserveTotal;//预约成功的数量
    private Byte userOrderStatus;//用户预定订单的状态
    private Timestamp adminEndTime;//截稿时间
    private String orderCode;
    private String email;
    private Integer pid;
    private Timestamp createdAt;//创建时间
    private String result;
    private Integer total;//订单的文章数量
    private Integer appointTotal;
}
