package cn.hlvan.form;

import lombok.Data;

@Data
public class AuditingEssayForm {
    private Integer id;
    private Byte status;
    private Integer userId;
    private String result;
    private Double originalLevel;
}
