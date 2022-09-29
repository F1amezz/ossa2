package com.ossjk.qlh.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossjk.core.system.api.IAreaCommonApi;
import com.ossjk.core.system.dto.AreaDto;
import com.ossjk.qlh.system.entity.Area;
import com.ossjk.qlh.system.service.IAreaService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: AreaCommonApiImpl
 * @Description: 地区公共Api
 * @author: chair
 * @date: 2021年3月14日 上午2:04:14
 */
@Service
public class AreaCommonApiImpl implements IAreaCommonApi {
	@Autowired
	private IAreaService areaService;

	@Override
	public List<AreaDto> getAllAreas() {
		return areaService.list().stream().map(area -> {
			return BeanUtil.copyProperties(area, AreaDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public AreaDto getAreaById(String id) {
		Area area = areaService.getById(id);
		AreaDto areaDto = Convert.convert(AreaDto.class, area);
		return areaDto;
	}

	@Override
	public Object getColumnByField(String tableName, String columnName, String whereName, Object whereValue) {
		return areaService.getColumnByField(tableName, columnName, whereName, whereValue);
	}
}
