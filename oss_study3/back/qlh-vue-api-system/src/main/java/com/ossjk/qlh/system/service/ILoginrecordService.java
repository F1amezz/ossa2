package com.ossjk.qlh.system.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.entity.Loginrecord;
import com.ossjk.qlh.system.vo.LoginrecordVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: LoginrecordService
 * @Description: 登录记录-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface ILoginrecordService extends IService<Loginrecord> {
	Page pageVo(Page page, QueryWrapper<LoginrecordVo> queryWrapper);

	List<Integer> countYear(Integer year, String uid);

	LoginrecordVo getVoById(Serializable id);

}
