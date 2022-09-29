package com.ossjk.qlh.components.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.IUploadCommonApi;
import com.ossjk.core.system.dto.UploadDto;
import com.ossjk.core.vo.ResponseBean;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.components.controller
 * @ClassName: UploadComponentContrller
 * @Description: 上传组件-控制器
 * @author: chair
 * @date: 2021年7月25日 下午10:46:32
 */
@Api(tags = "上传组件-控制器")
@RestController
@Import(cn.hutool.extra.spring.SpringUtil.class)
@RequestMapping("/components/uploadComponent")
public class UploadComponentContrller extends BaseController {

	@Autowired
	private SpringUtil springUtil;

	@ApiOperation(value = "文件上传")
	@PostMapping("/fileUpload")
	public ResponseBean fileUpload(MultipartFile file, UploadDto uploadDto) throws Exception {
		String model = uploadDto.getModel();
		IUploadCommonApi iUploadCommonApi = springUtil.getBean(model, IUploadCommonApi.class);
		if (ObjectUtil.isNull(iUploadCommonApi)) {
			return ResponseBean.Fail("model:" + model + ",填写错误。请联系后端人员。");
		}
		uploadDto.setName(file.getName());
		uploadDto.setOriginalFilename(file.getOriginalFilename());
		uploadDto.setInputStream(file.getInputStream());
		uploadDto.setEmpty(file.isEmpty());
		uploadDto.setContentType(file.getContentType());
		uploadDto.setSize(file.getSize());
		return iUploadCommonApi.upload(uploadDto);

	}

}
