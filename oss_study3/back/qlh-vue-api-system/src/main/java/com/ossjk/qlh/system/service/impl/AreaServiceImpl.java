package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.qlh.system.entity.Area;
import com.ossjk.qlh.system.mapper.AreaMapper;
import com.ossjk.qlh.system.service.IAreaService;

/**
 * Copyright 2021-03-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: AreaServiceImpl
 * @Description: 地区表-服务实现类
 * @author: Jason
 * @date: 2021-03-08 10:31:26
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

	@Cacheable(cacheNames = CacheConstant.CACHE_AREA_PREFIX, unless = "#result == null")
	@Override
	public List<Area> list() {
		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>().orderByAsc("id", "level");
		return super.list(queryWrapper);
	}

	@Cacheable(cacheNames = CacheConstant.CACHE_AREA_PREFIX, key = "#pid", unless = "#result == null")
	@Override
	public List<Area> getByPid(String pid) {
		QueryWrapper<Area> queryWrapper = new QueryWrapper<Area>().eq("pid", pid).orderByAsc("id", "level");
		return super.list(queryWrapper);
	}

	@Cacheable(cacheNames = CacheConstant.CACHE_AREA_PREFIX, key = "#id", unless = "#result == null")
	@Override
	public Area getById(Serializable id) {
		return super.getById(id);
	}

	@CacheEvict(value = { CacheConstant.CACHE_AREA_PREFIX }, allEntries = true)
	@Override
	public boolean save(Area entity) {
		return super.save(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_AREA_PREFIX }, allEntries = true)
	@Override
	public boolean updateById(Area entity) {
		return super.updateById(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_AREA_PREFIX }, allEntries = true)
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}

	@Override
	public Object getColumnByField(String tableName, String columnName, String whereName, Object whereValue) {
		return baseMapper.getColumnByField(tableName, columnName, whereName, whereValue);
	}

}
