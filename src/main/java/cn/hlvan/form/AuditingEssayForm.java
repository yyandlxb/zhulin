package cn.hlvan.form;

import lombok.Data;

@Data
public class AuditingEssayForm {
    private Integer id;//文章id
    private Byte status;//状态0-待管理员审核，1-商家退稿，2-收稿成功，3-商家已打款，4待商家审核，5管理员退稿
    private Integer userId;
    private String result;//审核结果
    private Double originalLevel;//文章原创度
}
