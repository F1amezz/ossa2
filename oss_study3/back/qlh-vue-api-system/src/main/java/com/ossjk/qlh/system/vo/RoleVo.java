package com.ossjk.qlh.system.vo;

import java.util.List;

import com.ossjk.qlh.system.entity.Role;

import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: RoleVo
 * @Description: 角色Vo
 * @author: Jason
 * @date: 2021年1月24日 下午11:03:19
 */
@Data
public class RoleVo extends Role {

	private List<String> permissionIds;

}
