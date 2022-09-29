package com.ossjk.qlh.system.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.qlh.system.dto.OptionDictionaryDto;
import com.ossjk.qlh.system.entity.Dictionarydata;
import com.ossjk.qlh.system.mapper.DictionarydataMapper;
import com.ossjk.qlh.system.service.IDictionarydataService;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: DictionarydataServiceImpl
 * @Description: 数据字典数据-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class DictionarydataServiceImpl extends ServiceImpl<DictionarydataMapper, Dictionarydata>
		implements IDictionarydataService {

	@CacheEvict(value = { CacheConstant.CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX }, allEntries = true)
	@Override
	public boolean updateOptionDictionaryDto(OptionDictionaryDto optionDictionaryDto) {
		// 把已有的字典选项删除
		this.remove(new QueryWrapper<Dictionarydata>().eq("did", optionDictionaryDto.getDid()));
		this.saveBatch(optionDictionaryDto.getDictionarydatas());
		return true;
	}

}
