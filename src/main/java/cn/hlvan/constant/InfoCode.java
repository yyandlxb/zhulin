package cn.hlvan.constant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 大和
 * 
 * 返回码
 */
public class InfoCode {

	private int status;

	private String msg;

    public InfoCode(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static final InfoCode SUCCESS = new InfoCode(0, "成功");

	public static final InfoCode FAILURE = new InfoCode(1, "失败");
	
	public static final InfoCode Commit = new InfoCode(2, "请勿重复提交");
	
	public static final InfoCode LOGIN_TIME_OUT = new InfoCode(3, "失败，登陆失效");
	
	public static final InfoCode CODE_TIME_OUT = new InfoCode(4, "失败，验证码超时");
	
	public static final InfoCode PWD_ERROR_AUTH = new InfoCode(5, "失败，没有权限修改");
	
	public static final InfoCode FAILURE_Role = new InfoCode(6, "失败,没有权限登录");
	
	public static final InfoCode FAILURE_BUSINESS = new InfoCode(7, "权限不足");

	public static final InfoCode EXIT_CODE = new InfoCode(8, "失败，验证码不正确");

    public static final InfoCode MESSAGE_ERROR = new InfoCode(1001, "验证码不正确");
	public static final InfoCode REGIST_ERROR = new InfoCode(1002, "该用户已存在");
    public static final InfoCode AUTH_ERROR = new InfoCode(1004, "用户名或密码错误");

	
	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}
