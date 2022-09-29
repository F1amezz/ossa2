package com.ossjk.qlh.system.service.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.config.mvc.ResourceMappersProperties.ResourceMapper;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.api.IUploadCommonApi;
import com.ossjk.core.system.dto.UploadDto;
import com.ossjk.core.vo.ResponseBean;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: FileSystemUploadApiImpl
 * @Description: 文件系统上传Api实现类
 * @author: chair
 * @date: 2021年7月24日 下午4:30:52
 */
@Service("fileSystem")
public class FileSystemUploadApiImpl implements IUploadCommonApi {
	@Autowired
	private ResourceMappersProperties mappersProperties;
	@Autowired
	private HttpServletRequest request;

	@Override
	public ResponseBean upload(UploadDto uploadDto) {

		List<ResourceMapper> resourceMappers = mappersProperties.getMappers();
		Optional<ResourceMapper> optional = resourceMappers.stream().filter(t -> {
			return StrUtil.equals(t.getUri(), uploadDto.getUri());
		}).findFirst();

		if (!optional.isPresent()) {
			return ResponseBean.Fail("uri:" + uploadDto.getUri() + ",填写错误。请联系后端人员。");
		}
		// 过滤mappersProperties
		String uuid = RandomUtil.randomUUID();
		String fileName = uuid + "-" + uploadDto.getOriginalFilename();
		ResourceMapper resourceMapper = optional.get();
		String filePath = resourceMapper.getFile();
		// 保存到文件里面
		FileUtil.writeFromStream(uploadDto.getInputStream(), new File(filePath, fileName));
		String url = uploadDto.getUri() + "/" + fileName;
		url = getLocalUrl(request) + url;
		return ResponseBean.Success(Constant.RESPONSE_MSG_SUCCESS, url);
	}

	public String getLocalUrl(HttpServletRequest request) {
		String url = "";
		url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		return url;
	}

}
