package com.ossjk.qlh.sms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.sms.entity.Sms;
import com.ossjk.qlh.sms.service.ISmsService;
import com.ossjk.qlh.sms.vo.SmsVo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2021-04-05 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.sms.controller
 * @ClassName: SmsController
 * @Description: 站内信息表-控制器
 * @author: chair
 * @date: 2021-04-05 22:44:33
 */
@Api(tags = "站内信息表")
@LogModule(value = "站内信息管理")
@RestController
@RequestMapping("/sms/sms")
public class SmsController extends BaseController {

	@Autowired
	private ISmsService iSmsService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;

	@ApiOperation(value = "列表")
	@RequiresPermissions("00200005")
	@GetMapping("/list")
	public ResponseBean<Page<SmsVo>> list(@ApiParam(value = "标题") @RequestParam(name = "title", required = false) String title, @ApiParam(value = "发送者id") @RequestParam(name = "sid", required = false) String sid, @ApiParam(value = "阅读状态") @RequestParam(name = "rstate", required = false) String rstate, @ApiParam(value = "内容") @RequestParam(name = "content", required = false) String content, @ApiParam(value = "内容级别") @RequestParam(name = "level", required = false) String level, @ApiParam(value = "内容类型") @RequestParam(name = "type", required = false) String type, Page page, HttpServletRequest request) {
		QueryWrapper<Sms> queryWrapper = new QueryWrapper<Sms>();
		if (StrUtil.isNotBlank(title)) {
			queryWrapper.like("title", title);
		}
		if (StrUtil.isNotBlank(sid)) {
			queryWrapper.eq("sid", sid);
		}
		if (StrUtil.isNotBlank(rstate)) {
			queryWrapper.eq("rstate", rstate);
		}
		if (StrUtil.isNotBlank(content)) {
			queryWrapper.like("content", content);
		}
		if (StrUtil.isNotBlank(level)) {
			queryWrapper.eq("level", level);
		}
		if (StrUtil.isNotBlank(type)) {
			queryWrapper.eq("type", type);
		}
		queryWrapper.eq("rid", request.getAttribute(Constant.REQUEST_ATTRIBUTE_UID));
		queryWrapper.orderByDesc("stime");
		return ResponseBean.Success(iSmsService.pageVo(page, queryWrapper));
	}

	@ApiOperation(value = "查看")
	@RequiresPermissions("002000050000")
	@GetMapping("/toRead")
	public ResponseBean<Sms> toRead(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Sms sms = iSmsService.getVoById(id);
		// 更新阅读状态
		if (ObjectUtil.isNotNull(sms)) {
			// 阅读信息
			List<String> ids = new ArrayList();
			ids.add(id);
			iSmsService.readBatch(ids);
			return ResponseBean.Success(sms);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "批量查看")
	@RequiresPermissions("002000050005")
	@PutMapping("/readBatch")
	public ResponseBean<Sms> readBatch(@RequestBody String[] ids) {
		// 更新阅读状态
		if (iSmsService.readBatch(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
