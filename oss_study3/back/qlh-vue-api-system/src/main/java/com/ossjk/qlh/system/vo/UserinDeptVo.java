package com.ossjk.qlh.system.vo;

import com.ossjk.core.base.entity.BaseEntity;
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
@ApiModel(value = "部门内的员工")
public class UserinDeptVo extends BaseEntity<UserinDeptVo> {


	@ApiModelProperty(value = "员工ID", required = false)
	private String id;

	@ApiModelProperty(value = "员工姓名", required = false)
	private String name;

}
