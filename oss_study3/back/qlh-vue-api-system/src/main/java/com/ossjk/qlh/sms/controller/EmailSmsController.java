package com.ossjk.qlh.sms.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.sms.dto.EmailSmsDto;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.service.IEmailService;
import com.ossjk.qlh.sms.service.ITemplatesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.controller
 * @ClassName: EmailSmsController
 * @Description: 邮件信息-控制器
 * @author: chair
 * @date: 2021年4月14日 上午11:48:48
 */
@Api(tags = "邮件信息")
@LogModule(value = "邮件信息")
@RestController
@RequestMapping("/sms/emailSms")
public class EmailSmsController extends BaseController {
	@Autowired
	private IEmailService iEmailService;
	@Autowired
	private ITemplatesService iTemplatesService;

	@ApiOperation(value = "发送信息")
	@RequiresPermissions("00200010")
	@GetMapping("/toSend")
	public ResponseBean toSend() {
		QueryWrapper<Templates> queryWrapper = new QueryWrapper();
		//类型 1-站内信、2-邮件
		queryWrapper.eq("type", "2").orderByDesc("crtm");
		List<Templates> templates = iTemplatesService.list(queryWrapper);
		return ResponseBean.Success(templates);
	}

	@ApiOperation(value = "发送信息")
	@RequiresPermissions("00200010")
	@PostMapping("/send")
	public ResponseBean send(@RequestBody EmailSmsDto emailSmsDto) {
		iEmailService.sendEmail(emailSmsDto);
		return ResponseBean.Success();
	}
}
