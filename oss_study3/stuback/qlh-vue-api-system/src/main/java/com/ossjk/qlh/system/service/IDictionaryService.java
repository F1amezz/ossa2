package com.ossjk.qlh.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.dto.OptionDictionaryDto;
import com.ossjk.qlh.system.entity.Dictionary;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: DictionaryService
 * @Description: 数据字典-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface IDictionaryService extends IService<Dictionary> {

	Dictionary getOneByCode(String code);


}
