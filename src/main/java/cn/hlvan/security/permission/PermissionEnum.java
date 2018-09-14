package cn.hlvan.security.permission;


public enum PermissionEnum {

    ORDER("订单管理"),
    ORDINARY_USER("普通用户管理"),
    WRITER_ORDER("我的订单"),
    APPLY_FINANCE("资金申请"),
    DISTRIBUTE_ORDER("分配订单"),
    ADMIN_USER("管理员用户管理");
    private String name;

    PermissionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
