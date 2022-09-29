package com.ossjk.qlh.system.service.impl;


import cn.hutool.core.util.IdUtil;
import com.ossjk.config.alioss.AliyunOssUtil;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.api.IUploadCommonApi;
import com.ossjk.core.system.dto.UploadDto;
import com.ossjk.core.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: AliyunOssUploadApiImpl
 * @Description: 阿里云Oss上传Api实现类
 * @author: chair
 * @date: 2021/10/6 0:00
 */
@Service("aliyunOss")
public class AliyunOssUploadApiImpl implements IUploadCommonApi {
    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Override
    public ResponseBean upload(UploadDto uploadDto) {
        //生成随机文件名称
        String uuid = IdUtil.randomUUID();
        String fileName = uuid + "-" + uploadDto.getOriginalFilename();
        String url=aliyunOssUtil.putObject(uploadDto.getUri()+"/",fileName,uploadDto.getInputStream());
        return ResponseBean.Success(Constant.RESPONSE_MSG_SUCCESS, url);
    }
}
