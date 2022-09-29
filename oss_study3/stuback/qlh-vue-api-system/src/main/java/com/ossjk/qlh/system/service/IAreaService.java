package com.ossjk.qlh.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.entity.Area;

/**
 * Copyright 2021-03-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: AreaService
 * @Description: 地区表-服务类接口
 * @author: Jason
 * @date: 2021-03-08 10:31:26
 */
public interface IAreaService extends IService<Area> {

	List<Area> list();

	List<Area> getByPid(String pid);

	/**
	 * 根据数据表名和字段名，查询值
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	Object getColumnByField(String tableName, String columnName, String whereName, Object whereValue);
}
