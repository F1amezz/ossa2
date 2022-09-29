package com.ossjk.qlh.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.mapper.FeedbackMapper;
import com.ossjk.qlh.study.service.IFeedbackService;
import com.ossjk.qlh.study.vo.FeedbackVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Copyright  2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service.impl
 * @ClassName: FeedbackServiceImpl
 * @Description: 总结反馈表-服务实现类
 * @author: Rick.yang
 * @date:  2022-06-13 18:03:21 
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {


    @Override
    public Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> qw) {
        return this.baseMapper.selectVo(page,qw);
    }

    @Override
    public List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> qw) {
        return this.baseMapper.selectVoList(qw);
    }

    @Override
    public FeedbackVo selectVoById(String id) {
        return this.baseMapper.selectVoById(id);
    }





}
