package com.ossjk.config.mvc;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.mvc
 * @ClassName: ResourceMappersProperties
 * @Description: 资源映射配置
 * @author: chair
 * @date: 2020年12月8日 下午5:12:50
 */
@Slf4j
@ConfigurationProperties(prefix = "resource")
@Data
public class ResourceMappersProperties {

	private List<ResourceMapper> mappers;

	@Data
	public static class ResourceMapper {
		private String uri;
		private String file;
	}

}
