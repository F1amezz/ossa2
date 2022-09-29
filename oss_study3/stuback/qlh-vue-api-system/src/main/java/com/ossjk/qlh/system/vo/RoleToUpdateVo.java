package com.ossjk.qlh.system.vo;

import java.util.List;

import com.ossjk.qlh.system.entity.Role;
import com.ossjk.qlh.system.entity.RoleDepartment;
import com.ossjk.qlh.system.entity.RolePermission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.vo
 * @ClassName: UserToUpdateVo
 * @Description: 角色去编辑Vo
 * @author: chair
 * @date: 2021年6月30日 下午4:11:23
 */
@Data
@ApiModel(value = "角色去编辑Vo")
public class RoleToUpdateVo {
	/**
	 * 角色信息
	 */
	@ApiModelProperty(value = "角色信息")
	private Role role;
	/**
	 * 角色权限
	 */
	@ApiModelProperty(value = "角色权限")
	private List<RolePermission> rolePermissions;
	/**
	 * 角色部门
	 */
	@ApiModelProperty(value = "角色部门")
	private RoleDepartment roleDepartment;
}
