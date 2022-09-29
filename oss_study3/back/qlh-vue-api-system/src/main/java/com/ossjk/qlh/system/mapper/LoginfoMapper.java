package com.ossjk.qlh.system.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.system.entity.Loginfo;
import com.ossjk.qlh.system.vo.LoginfoVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: LoginfoMapper
 * @Description: 日志信息-Mapper
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface LoginfoMapper extends BaseMapper<Loginfo> {

	Page pageVo(Page page, @Param(Constants.WRAPPER) QueryWrapper<LoginfoVo> queryWrapper);

	LoginfoVo getVoById(Serializable id);
}
