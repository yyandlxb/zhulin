package cn.hlvan.security;

import cn.hlvan.security.session.SessionManager;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class AuthorizedUser implements Serializable, SessionManager.Indexed {

    private static final long serialVersionUID = 3933889168576006480L;

    private String name;
    private Integer id;
    private String account;
    private Integer type;
    private Integer status;
    private Integer pid;

    @Override
    public String getIndexValue() {
        return id+name;
    }
}
