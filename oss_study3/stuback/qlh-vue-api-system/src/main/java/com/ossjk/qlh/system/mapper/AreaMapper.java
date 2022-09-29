package com.ossjk.qlh.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.system.entity.Area;

/**
 * Copyright 2021-03-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: AreaMapper
 * @Description: 地区表-Mapper
 * @author: Jason
 * @date: 2021-03-08 10:31:26
 */
public interface AreaMapper extends BaseMapper<Area> {

	Object getColumnByField(@Param("tableName") String tableName, @Param("columnName") String columnName,
			@Param("whereName") String whereName, @Param("whereValue") Object whereValue);
	

}
