package com.ossjk.config.mail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ossjk.core.exception.QlhRunTimeException;
import com.ossjk.core.system.api.IDictionaryCommonApi;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.DictionaryDto;
import com.ossjk.core.system.dto.EmailSetting;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.mail
 * @ClassName: MailConfig 
 * @Description: 邮件配置
 * @author: chair
 * @date: 2021年3月7日 下午11:01:55
 */
@Configuration
@Slf4j
public class MailConfig {
	@Autowired
	private ISystemCommonApi iSystemCommonApi;

	@Bean
	public MailAccount mailAccount() {
		if (ObjectUtil.isEmpty(iSystemCommonApi)) {
			QlhRunTimeException qlhException = new QlhRunTimeException("MailConfig 注入入库组件出错,注入公共组件出错。");
			log.error("异常：" + qlhException.getMessage(), qlhException);
			throw qlhException;
		}
		MailAccount account = new MailAccount();
		EmailSetting emailSetting = iSystemCommonApi.getEmailSetting();
		if (ObjectUtil.isNotEmpty(emailSetting)) {
			account.setHost(emailSetting.getHost());
			account.setPass(emailSetting.getPass());
			account.setPort(emailSetting.getPort());
			account.setUser(emailSetting.getUser());
			account.setFrom(emailSetting.getMailSender());
		}
		account.setAuth(true);
		// 使用ssl协议
		account.setSslEnable(true);
		return account;
	}
}
