package cn.hlvan.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class AuthorizedUser implements Serializable{

    private static final long serialVersionUID = 3933889168576006480L;

    private String name;
    private Integer id;
    private String account;
    private Integer type;
    private Integer enabled;

}
