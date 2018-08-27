package cn.hlvan.security.permission;


public enum PermissionEnum {

    DEPART("部门管理"),
    ROLE("角色管理"),
    ACCOUNT("账户管理");
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
