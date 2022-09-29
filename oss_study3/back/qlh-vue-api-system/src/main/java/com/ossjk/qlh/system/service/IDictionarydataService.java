package com.ossjk.qlh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.dto.OptionDictionaryDto;
import com.ossjk.qlh.system.entity.Dictionarydata;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: DictionarydataService
 * @Description: 数据字典数据-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface IDictionarydataService extends IService<Dictionarydata> {

	boolean updateOptionDictionaryDto(OptionDictionaryDto optionDictionaryDto);

	

}
