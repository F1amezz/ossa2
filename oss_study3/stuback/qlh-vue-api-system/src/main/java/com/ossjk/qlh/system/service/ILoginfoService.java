package com.ossjk.qlh.system.service;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.entity.Loginfo;
import com.ossjk.qlh.system.vo.LoginfoVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: LoginfoService
 * @Description: 日志信息-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface ILoginfoService extends IService<Loginfo> {

	Page pageVo(Page page, QueryWrapper<LoginfoVo> queryWrapper);

	LoginfoVo getVoById(Serializable id);
}
