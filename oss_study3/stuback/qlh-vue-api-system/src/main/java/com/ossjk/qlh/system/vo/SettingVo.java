package com.ossjk.qlh.system.vo;

import com.ossjk.core.system.dto.EmailSetting;
import com.ossjk.core.system.dto.SystemSetting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.vo
 * @ClassName: SettingVo 
 * @Description: 设置Vo
 * @author: chair
 * @date: 2021年6月30日 下午4:16:44
 */
@Data
@ApiModel(value = "设置Vo")
public class SettingVo {
	/**
	 * 系统设置
	 */
	@ApiModelProperty("系统设置")
	private SystemSetting systemSetting;
	/**
	 * 邮件设置
	 */
	@ApiModelProperty("邮件设置")
	private EmailSetting emailSetting;
}
