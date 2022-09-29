package com.ossjk.qlh.study.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.mapper.SelfevaluationMapper;
import com.ossjk.qlh.study.service.ISelfevaluationService;
import com.ossjk.qlh.study.vo.SelfevaluationVo;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service.impl
 * @ClassName: SelfevaluationServiceImpl
 * @Description: 自我评价表-服务实现类
 * @author: Rick.yang
 * @date: 2022-06-14 17:12:17
 */
@Service
public class SelfevaluationServiceImpl extends ServiceImpl<SelfevaluationMapper, Selfevaluation>
        implements ISelfevaluationService {

    @Override
    public Page<SelfevaluationVo> selectVo(Page page, QueryWrapper<SelfevaluationVo> qw) {
        return this.baseMapper.selectVo(page, qw);
    }

    @Override
    public List<SelfevaluationVo> selectVo(QueryWrapper<SelfevaluationVo> qw) {
        return this.baseMapper.selectVo(qw);
    }



}
