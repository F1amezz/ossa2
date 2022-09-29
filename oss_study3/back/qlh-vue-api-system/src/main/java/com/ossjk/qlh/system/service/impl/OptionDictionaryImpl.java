package com.ossjk.qlh.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.qlh.system.dto.OptionDictionaryDto;
import com.ossjk.qlh.system.entity.Dictionary;
import com.ossjk.qlh.system.entity.Dictionarydata;
import com.ossjk.qlh.system.mapper.DictionaryMapper;
import com.ossjk.qlh.system.service.IDictionarydataService;
import com.ossjk.qlh.system.service.IOptionDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: DictionaryServiceImpl
 * @Description: 选项字典-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class OptionDictionaryImpl extends ServiceImpl<DictionaryMapper, Dictionary>
		implements IOptionDictionaryService {
	@Autowired
	private IDictionarydataService iDictionarydataService;

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean save(Dictionary entity) {
		return super.save(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean updateById(Dictionary entity) {
		return super.updateById(entity);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean updateOption(OptionDictionaryDto optionDictionaryDto) {
		return iDictionarydataService.updateOptionDictionaryDto(optionDictionaryDto);
	}

	@Cacheable(cacheNames = CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX, key = "#dkey", unless = "#result == null")
	@Override
	public List<Dictionarydata> listOptionDictionary(String dkey) {
		List<Dictionarydata> dictionarydatas = null;
		// 查询所有的选项id
		QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<Dictionary>();
		// 是否禁用：1-是、2-否
		queryWrapper.eq("isdisable", "1");
		queryWrapper.eq("dkey", dkey);
		List<Dictionary> list = this.list(queryWrapper);
		List<String> ids = list.stream().map(i -> i.getId()).collect(Collectors.toList());
		if (!CollUtil.isEmpty(ids)) {
			dictionarydatas = iDictionarydataService
					.list(new QueryWrapper<Dictionarydata>().in("did", ids).eq("isdisable", "1").orderByAsc("sort"));
		}
		// 查询选项值
		return dictionarydatas;
	}

}
