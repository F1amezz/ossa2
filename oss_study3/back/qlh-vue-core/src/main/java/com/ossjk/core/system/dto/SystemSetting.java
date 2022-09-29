package com.ossjk.core.system.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.dto
 * @ClassName: SystemSetting
 * @Description: 系统设置
 * @author: chair
 * @date: 2021年3月3日 下午10:41:36
 */
@Data
public class SystemSetting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 系统标题
	 */
	@ApiModelProperty(value = "系统标题")
	private String title;
	/**
	 * 系统名称
	 */
	@ApiModelProperty(value = "系统名称")
	private String systemName;
	/**
	 * 系统图标
	 */
	@ApiModelProperty(value = "系统图标")
	private String icon;
	/**
	 * 系统备案
	 */
	@ApiModelProperty(value = "系统备案")
	private String icp;
	/**
	 * 底部内容
	 */
	@ApiModelProperty(value = "底部内容")
	private String footer;
}
