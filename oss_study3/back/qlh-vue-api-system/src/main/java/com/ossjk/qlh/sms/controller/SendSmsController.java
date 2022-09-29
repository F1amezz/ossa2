package com.ossjk.qlh.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.sms.dto.SendSmsDto;
import com.ossjk.qlh.sms.entity.Sms;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.service.ISmsService;
import com.ossjk.qlh.sms.service.ITemplatesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright 2021-04-05 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.sms.controller
 * @ClassName: SmsController
 * @Description: 发送站内信息-控制器
 * @author: chair
 * @date: 2021-04-05 22:44:33
 */
@Api(tags = "发送站内信息")
@LogModule(value = "发送站内信息")
@RestController
@RequestMapping("/sms/sendSms")
public class SendSmsController extends BaseController {

	@Autowired
	private ISmsService iSmsService;
	@Autowired
	private ITemplatesService iTemplatesService;

	@ApiOperation(value = "发送信息")
	@RequiresPermissions("00200010")
	@GetMapping("/toSend")
	public ResponseBean toSend() {
		QueryWrapper<Templates> queryWrapper = new QueryWrapper();
		// 类型 1-站内信、2-邮件
		queryWrapper.eq("type", "1").orderByDesc("crtm");
		List<Templates> templates = iTemplatesService.list(queryWrapper);
		return ResponseBean.Success(templates);
	}

	@ApiOperation(value = "发送信息")
	@RequiresPermissions("00200000")
	@PostMapping("/send")
	public ResponseBean send(@RequestBody SendSmsDto sendSmsDto, HttpServletRequest request) {
		// 拿到用户id
		String uid = (String) request.getAttribute(Constant.REQUEST_ATTRIBUTE_UID);
		// 设置发送人id
		sendSmsDto.setSid(uid);
		if (iSmsService.sendSms(sendSmsDto)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}

	}

}
