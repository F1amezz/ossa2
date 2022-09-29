package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.system.entity.Loginrecord;
import com.ossjk.qlh.system.mapper.LoginrecordMapper;
import com.ossjk.qlh.system.service.ILoginrecordService;
import com.ossjk.qlh.system.vo.LoginrecordVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: LoginrecordServiceImpl
 * @Description: 登录记录-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class LoginrecordServiceImpl extends ServiceImpl<LoginrecordMapper, Loginrecord> implements ILoginrecordService {

	@Override
	public Page pageVo(Page page, QueryWrapper<LoginrecordVo> queryWrapper) {
		return baseMapper.pageVo(page, queryWrapper);
	}

	@Override
	public List<Integer> countYear(Integer year, String uid) {
		return baseMapper.countYear(year,uid);
	}

	@Override
	public LoginrecordVo getVoById(Serializable id) {
		return baseMapper.getVoById(id);
	}

}
