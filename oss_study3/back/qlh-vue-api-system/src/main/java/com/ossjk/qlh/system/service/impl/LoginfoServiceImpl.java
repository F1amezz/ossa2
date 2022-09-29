package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.system.entity.Loginfo;
import com.ossjk.qlh.system.mapper.LoginfoMapper;
import com.ossjk.qlh.system.service.ILoginfoService;
import com.ossjk.qlh.system.vo.LoginfoVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: LoginfoServiceImpl
 * @Description: 日志信息-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements ILoginfoService {

	@Override
	public Page pageVo(Page page, QueryWrapper<LoginfoVo> queryWrapper) {
		return baseMapper.pageVo(page, queryWrapper);
	}

	@Override
	public LoginfoVo getVoById(Serializable id) {
		return baseMapper.getVoById(id);
	}

}
