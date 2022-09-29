package com.ossjk.core.system.dto;

import java.io.InputStream;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.dto
 * @ClassName: UploadDto 
 * @Description: 上传Dto
 * @author: chair
 * @date: 2021年7月25日 下午10:45:54
 */
@ApiModel(value = "上传Dto")
@Data
public class UploadDto implements Serializable {
	/**
	 * 上传模式 <br>
	 * base64 直接把图片转为base64<br>
	 * -----------------------<br>
	 * 以下上传都会上传都后台<br>
	 * -----------------------<br>
	 * fileSystem 服务器把图片保存到文件服务器<br>
	 */
	private String model;
	/**
	 * 保存路径
	 */
	private String uri;

	private String name;

	private String originalFilename;

	private String contentType;

	private boolean isEmpty;

	private long size;

	private InputStream inputStream;

}
