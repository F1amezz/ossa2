package com.ossjk.qlh.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.vo
 * @ClassName: UserVo
 * @Description: 用户Vo
 * @author: chair
 * @date: 2021年3月17日 下午10:00:19
 */
@Data
@ApiModel(value = "用户2Vo")
public class User2Vo {

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "id", required = false)
	private String id;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "名称", required = false)
	private String name;

}
