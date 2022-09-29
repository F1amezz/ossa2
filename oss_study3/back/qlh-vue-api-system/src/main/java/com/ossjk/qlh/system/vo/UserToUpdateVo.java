package com.ossjk.qlh.system.vo;

import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.entity.UserRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.vo
 * @ClassName: UserToUpdateVo
 * @Description: 用户去编辑Vo
 * @author: chair
 * @date: 2021年6月30日 下午4:11:23
 */
@Data
@ApiModel(value = "用户去编辑Vo")
public class UserToUpdateVo {

	@ApiModelProperty(value = "用户内容")
	private User user;
	
	@ApiModelProperty(value = "用户角色")
	private UserRole userRole;

}
