package com.ossjk.qlh.components.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.IDictionaryCommonApi;
import com.ossjk.core.system.dto.DictionarydataDto;
import com.ossjk.core.vo.ResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Copyright 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.components.controller
 * @ClassName: OptionDictionaryComponentContrller
 * @Description: TODO
 * @author: Jason
 * @date: 2021年2月27日 下午10:15:13
 */
@Api(tags = "OptionDictionaryComponent-控制器")
@RestController
@RequestMapping("/components/optionDictionaryComponentContrller")
public class OptionDictionaryComponentContrller extends BaseController {
	@Autowired
	private IDictionaryCommonApi iDictionaryCommonApi;

	@ApiOperation(value = "选项字典列表")
	@GetMapping("/listOptionDictionary")
	public ResponseBean<List<DictionarydataDto>> listOptionDictionary(@ApiParam(value = "选项字典编号") @RequestParam(name = "dkey", required = true) String dkey) {
		return ResponseBean.Success(iDictionaryCommonApi.listDictionaryOption(dkey));
	}

}
