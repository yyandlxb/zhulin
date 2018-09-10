package cn.hlvan.constant;

public class OrderEssayStatus {
    public static final byte ADMIN_WAIT_AUDITING = 0;//管理员待审核
    public static final byte MERCHANT_REJECTION = 1;//商家退稿
    public static final byte ACCEPT_SUCCESS = 2;//商家收稿成功
    public static final byte ALREADY_PAY = 3;//管理员已打款
    public static final byte MERCHANT_WAIT_AUDITING = 4;//商户待核通过
    public static final byte ADMIN_REJECTION = 5;//管理员退稿
}
