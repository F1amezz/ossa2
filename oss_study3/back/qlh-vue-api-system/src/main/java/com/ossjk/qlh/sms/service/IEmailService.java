package com.ossjk.qlh.sms.service;

import com.ossjk.qlh.sms.dto.EmailSmsDto;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.service
 * @ClassName: IEmailService 
 * @Description: 邮件消息-服务类接口
 * @author: chair
 * @date: 2021年4月28日 下午4:51:34
 */
public interface IEmailService {

	void sendEmail(EmailSmsDto emailSmsDto);
	
}
