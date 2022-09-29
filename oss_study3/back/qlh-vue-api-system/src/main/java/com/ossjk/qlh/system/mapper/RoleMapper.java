package com.ossjk.qlh.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.system.entity.Role;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: RoleMapper
 * @Description: 角色表-Mapper
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface RoleMapper extends BaseMapper<Role> {

	List<Role> selectByUid(@Param("uid") String uid);

	@Select("SELECT jxfm FROM role WHERE CODE=#{rcode}")
	String selectJXFMByRname(@Param("rcode") String rcode);

}
