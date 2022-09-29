package com.ossjk.core.system.api;

import java.util.List;

import com.ossjk.core.system.dto.AreaDto;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.api
 * @ClassName: IAreaCommonApi
 * @Description: 地区公共业务接口
 * @author: Jason
 * @date: 2021年3月11日 下午9:54:51
 */
public interface IAreaCommonApi {

	List<AreaDto> getAllAreas();

	AreaDto getAreaById(String id);

	Object getColumnByField(String tableName, String columnName, String whereName, Object whereValue);

}
