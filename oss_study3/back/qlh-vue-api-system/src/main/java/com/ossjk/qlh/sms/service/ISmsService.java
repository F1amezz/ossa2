package com.ossjk.qlh.sms.service;

import java.util.Collection;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.sms.dto.SendSmsDto;
import com.ossjk.qlh.sms.entity.Sms;

/**
 * Copyright 2021-04-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.service
 * @ClassName: SmsService
 * @Description: 站内信息表-服务类接口
 * @author: chair
 * @date: 2021-04-05 22:44:33
 */
public interface ISmsService extends IService<Sms> {

	Boolean sendSms(SendSmsDto sendSmsDto);

	Boolean readBatch(Collection<String> idList);

	Page pageVo(Page page, Wrapper wrapper);

	Sms getVoById(String id);
}
