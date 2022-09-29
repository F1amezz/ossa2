package com.ossjk.qlh.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.qlh.sms.dto.EmailSmsDto;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.service.IEmailService;
import com.ossjk.qlh.sms.service.ITemplatesService;

import cn.hutool.core.util.StrUtil;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.service.impl
 * @ClassName: EmailServiceImpl
 * @Description: 邮件信息-服务实现类
 * @author: chair
 * @date: 2021年4月28日 下午4:58:02
 */
@Service
public class EmailServiceImpl implements IEmailService {
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private ITemplatesService iTemplatesService;

	@Override
	public void sendEmail(EmailSmsDto emailSmsDto) {
		// 判断是否模版解释
		if (emailSmsDto.getSendType() == 2) {
			// 模版标识查重
			Templates templates = iTemplatesService.getOne(new QueryWrapper<Templates>().eq("type", "2").eq("code", emailSmsDto.getTcode()));
			emailSmsDto.setContent(StrUtil.format(templates.getContent(), JSON.parseObject(emailSmsDto.getParameter()).getInnerMap()));
		}
		iSystemCommonApi.sendEmail(emailSmsDto.getTo(), emailSmsDto.getSubject(), emailSmsDto.getContent(), true);
	}

}
