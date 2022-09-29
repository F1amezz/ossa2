package com.ossjk.qlh.system.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.system.entity.Loginrecord;
import com.ossjk.qlh.system.vo.LoginrecordVo;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: LoginrecordMapper
 * @Description: 登录记录-Mapper
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface LoginrecordMapper extends BaseMapper<Loginrecord> {

	Page pageVo(Page page, @Param(Constants.WRAPPER) QueryWrapper<LoginrecordVo> queryWrapper);

	List<Integer> countYear(@Param("year") Integer year, @Param("uid") String uid);

	LoginrecordVo getVoById(Serializable id);
}
