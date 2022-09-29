package com.ossjk.core.system.api;

import java.util.List;

import com.ossjk.core.system.dto.DictionaryDto;
import com.ossjk.core.system.dto.DictionarydataDto;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.api
 * @ClassName: IDictionaryCommonApi
 * @Description: 字典公共业务接口
 * @author: chair
 * @date: 2021年3月3日 下午10:08:37
 */
public interface IFieldColumnCommonApi {

	List<DictionarydataDto> listDictionaryOption(String key);

}
