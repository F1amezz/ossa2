package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.qlh.system.entity.Dictionary;
import com.ossjk.qlh.system.mapper.DictionaryMapper;
import com.ossjk.qlh.system.service.IDictionaryService;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: DictionaryServiceImpl
 * @Description: 数据字典-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {
	@Cacheable(cacheNames = CacheConstant.CACHE_DICTIONARY_DICTIONARY_PREFIX, key = "#code", unless = "#result == null")
	@Override
	public Dictionary getOneByCode(String code) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("code", code);
		return super.getOne(queryWrapper);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_DICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean save(Dictionary entity) {
		return super.save(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_DICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean updateById(Dictionary entity) {
		return super.updateById(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_DICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}

}
