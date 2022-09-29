package com.ossjk.qlh.system.vo;

import com.ossjk.qlh.system.entity.Student;

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
@ApiModel(value = "用户Vo")
public class StudentVo extends Student {

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id", required = false)
	private String did;
	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id", required = false)
	private String rid;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "部门名称", required = false)
	private String dname;
	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称", required = false)
	private String sname;


}
