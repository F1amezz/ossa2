package com.ossjk.qlh.sms.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.config.sms.SmsServer;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.qlh.sms.dto.SendSmsDto;
import com.ossjk.qlh.sms.entity.Sms;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.mapper.SmsMapper;
import com.ossjk.qlh.sms.service.ISmsService;
import com.ossjk.qlh.sms.service.ITemplatesService;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.service.IUserService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Copyright 2021-04-05 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.sms.service.impl
 * @ClassName: SmsServiceImpl
 * @Description: 站内信息表-服务实现类
 * @author: chair
 * @date: 2021-04-05 22:44:33
 */
@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements ISmsService {

	@Autowired
	private SmsServer smsServer;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private ITemplatesService iTemplatesService;

	@Override
	public Page pageVo(Page page, Wrapper wrapper) {
		return this.baseMapper.selectPageVo(page, wrapper);
	}

	@Override
	public Sms getVoById(String id) {
		return this.baseMapper.getVoById(id);
	}

	@Override
	public Boolean sendSms(SendSmsDto sendSmsDto) {

		// 判断是否模版解释
		if (sendSmsDto.getSendType() == 2) {
			Templates templates = iTemplatesService.getOne(new QueryWrapper<Templates>().eq("type", "1").eq("code", sendSmsDto.getTcode()));
			sendSmsDto.setContent(StrUtil.format(templates.getContent(), JSON.parseObject(sendSmsDto.getParameter()).getInnerMap()));
		}

		boolean result = false;
		// 阅读状态:1-未读、2-已读
		sendSmsDto.setRstate(1);
		// 发送时间
		sendSmsDto.setStime(new Date());
		// 发送类型 1-广播、2-个人
		if (StrUtil.equals(sendSmsDto.getStype(), "1")) {
			// 1-广播
			// 是否禁用：1-是、2-否
			List<User> users = iUserService.list(new QueryWrapper<User>().eq("isdisable", 2));
			if (ObjectUtil.isNotEmpty(users)) {
				List<Sms> smss = new ArrayList();
				for (User user : users) {
					Sms sms = BeanUtil.copyProperties(sendSmsDto, Sms.class, "stype", "rid");
					sms.setRid(user.getId());
					smss.add(sms);
				}
				result = this.saveBatch(smss);
				if (result) {
					for (Sms sms : smss) {
						iSystemCommonApi.notifyUpdateSms(sms.getRid());
					}
				}
			}
		} else {
			// 2-个人
			Sms sms = BeanUtil.copyProperties(sendSmsDto, Sms.class, "stype");

			result = this.save(sms);
			if (result) {
				iSystemCommonApi.notifyUpdateSms(sms.getRid());
			}
		}
		return result;
	}

	@Override
	public Boolean readBatch(Collection<String> idList) {
		List<Sms> smss = idList.stream().map((id) -> {
			Sms sms = this.getById(id);
			// 阅读状态:1-未读、2-已读
			sms.setRstate(2);
			sms.setRtime(new Date());
			return sms;
		}).collect(Collectors.toList());
		boolean result = this.updateBatchById(smss);
		if (ObjectUtil.isNotEmpty(smss)) {
			for (Sms sms : smss) {
				iSystemCommonApi.notifyUpdateSms(sms.getRid());
			}
		}
		return result;
	}
}
