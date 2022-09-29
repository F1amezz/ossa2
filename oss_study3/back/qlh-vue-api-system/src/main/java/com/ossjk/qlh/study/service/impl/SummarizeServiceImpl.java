package com.ossjk.qlh.study.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.mapper.SummarizeMapper;
import com.ossjk.qlh.study.service.ISummarizeService;
import com.ossjk.qlh.study.vo.SummarizeVo;

/**
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service.impl
 * @ClassName: SummarizeServiceImpl
 * @Description: -服务实现类
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
@Service
public class SummarizeServiceImpl extends ServiceImpl<SummarizeMapper, Summarize> implements ISummarizeService {

    @Override
    public Page<SummarizeVo> selectVo(Page page, QueryWrapper<SummarizeVo> qw) {
        return this.baseMapper.selectVo(page, qw);
    }

    @Override
    public List<SummarizeVo> selectVoList(QueryWrapper<SummarizeVo> qw) {
        return this.baseMapper.selectVoList(qw);
    }

    @Override
    public SummarizeVo selectVoById(String id) {
        return this.baseMapper.selectVoById(id);
    }



}
