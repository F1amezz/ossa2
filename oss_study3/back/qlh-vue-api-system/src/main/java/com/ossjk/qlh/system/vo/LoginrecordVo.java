package com.ossjk.qlh.system.vo;

import com.ossjk.qlh.system.entity.Loginrecord;

import io.swagger.annotations.ApiModelProperty;

public class LoginrecordVo extends Loginrecord {
	@ApiModelProperty(value = "用户名")
	private String uname;
	@ApiModelProperty(value = "手机")
	private String mobile;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
