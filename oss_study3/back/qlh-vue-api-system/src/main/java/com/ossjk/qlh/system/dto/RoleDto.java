package com.ossjk.qlh.system.dto;

import java.util.List;

import com.ossjk.qlh.system.entity.Role;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: RoleDto
 * @Description: 角色Dto
 * @author: Jason
 * @date: 2021年1月24日 下午11:03:19
 */
@ApiModel(value = "角色Dto")
@Data
public class RoleDto extends Role {

	private List<String> permissionIds;

	private String departmentId;

}
