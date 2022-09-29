package com.ossjk.core.system.api;

import java.io.IOException;

import com.ossjk.core.system.dto.UploadDto;
import com.ossjk.core.vo.ResponseBean;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.api
 * @ClassName: IUploadCommonApi 
 * @Description: 公共上传接口
 * @author: chair
 * @date: 2021年7月24日 下午9:15:21
 */
public interface IUploadCommonApi {
	
	/**
	 * 上传逻辑
	 * @param uploadDto
	 * @return
	 */
	ResponseBean upload(UploadDto uploadDto);
}
