package cn.hlvan.form;

import lombok.Data;

@Data
public class UserForm {
    private Integer id;
    private String phoneNumber;
    private String name;
    private String password;
    private String code;
}
