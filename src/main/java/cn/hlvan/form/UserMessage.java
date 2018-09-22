package cn.hlvan.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserMessage{
    @NotNull
    private String qq;//qq
    private String weChat;//微信号
    @NotNull
    private String email;//邮箱
    private String address;//住址
    private Integer sex;//性别 0-男，1-女
    private Integer age;//年龄
    private String profession;//职业
    private String good;//擅长领域
    private Integer fullTime;//工作类型 0-全职，1-兼职
    private String payPicture;//图片名称
    private String name;
}
