package cn.hlvan.view;

import lombok.Data;

@Data
public class User {
    private String name;
    private Integer id;
    private String account;
    private String type;
    private Integer status;
    private String phoneNumber;
    private String password;
    private String msgid;
    private String code;
    private String validCode;
}
