package cn.hlvan.constant;

public class OrderStatus {
    public static final byte AWAIT_AUDUTING =0;//待审核
    public static final byte AWAIT_RECEPTION =1;//待接单
    public static final byte ALREADY_PECEPTION =2;//已接单
    public static final byte AWAIT_COMMENT =3;//待点评
    public static final byte MERCHANT_COMPLETED =4;//商家已完成(已打款)
    public static final byte CANCEL =5;//取消
    public static final byte CLOSE =6;//关闭
    public static final byte ADMIN_COMPLETED =7;//管理员已完成(已打款)
}
