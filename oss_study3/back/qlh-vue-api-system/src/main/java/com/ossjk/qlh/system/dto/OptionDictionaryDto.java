package com.ossjk.qlh.system.dto;

import java.io.Serializable;
import java.util.List;

import com.ossjk.qlh.system.entity.Dictionary;
import com.ossjk.qlh.system.entity.Dictionarydata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.dto
 * @ClassName: OptionDictionaryDto
 * @Description: 选项字典Dto
 * @author: chair
 * @date: 2021年1月24日 下午11:03:19
 */
@ApiModel(value = "选项字典Dto")
@Data
public class OptionDictionaryDto implements Serializable {

	@ApiModelProperty(value = "字典id", required = true)
	private String did;
	@ApiModelProperty(value = "选项数据")
	private List<Dictionarydata> dictionarydatas;

}
