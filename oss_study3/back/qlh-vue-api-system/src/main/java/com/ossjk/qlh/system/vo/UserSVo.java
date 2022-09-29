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
@ApiModel(value = "用于计算工资的Vo")
public class UserSVo {

	@ApiModelProperty(value = "id", required = false)
	private String id;

	@ApiModelProperty(value = "名称", required = false)
	private String name;

	private String sidx;

	private String dy;



}
