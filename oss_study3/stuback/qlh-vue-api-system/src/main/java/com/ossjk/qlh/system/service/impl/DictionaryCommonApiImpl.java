package com.ossjk.qlh.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossjk.core.system.api.IDictionaryCommonApi;
import com.ossjk.core.system.dto.DictionarydataDto;
import com.ossjk.qlh.system.entity.Dictionarydata;
import com.ossjk.qlh.system.service.IOptionDictionaryService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: DictionaryCommonApiImpl
 * @Description: TODO
 * @author: chair
 * @date: 2021年3月3日 下午10:08:31
 */
@Service
public class DictionaryCommonApiImpl implements IDictionaryCommonApi {
	@Autowired
	private IOptionDictionaryService iOptionDictionaryService;

	@Override
	public List<DictionarydataDto> listDictionaryOption(String key) {
		List<Dictionarydata> dictionarydatas = iOptionDictionaryService.listOptionDictionary(key);
		if (ObjectUtil.isNotEmpty(dictionarydatas)) {
			return dictionarydatas.stream().map(dictionarydata -> {
				return BeanUtil.copyProperties(dictionarydata, DictionarydataDto.class);
			}).collect(Collectors.toList());
		}
		return null;
	}

}
