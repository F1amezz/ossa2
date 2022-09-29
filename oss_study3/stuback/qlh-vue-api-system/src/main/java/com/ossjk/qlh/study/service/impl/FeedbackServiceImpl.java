package com.ossjk.qlh.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.mapper.FeedbackMapper;
import com.ossjk.qlh.study.service.IFeedbackService;
import com.ossjk.qlh.study.vo.FeedbackVo;
import com.ossjk.qlh.system.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright  2022-07-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study.service.impl
 * @ClassName: FeedbackServiceImpl
 * @Description: 总结表-服务实现类
 * @author: Rick.yang
 * @date:  2022-07-05 16:48:09 
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IClazzService clazzService;


    @Override
    public Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> qw) {
        return this.baseMapper.selectVo(page, qw);
    }

    @Override
    public List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> qw) {
        return this.baseMapper.selectVoList(qw);
    }

    @Override
    public FeedbackVo selectVoById(String id) {
        return this.baseMapper.selectVoById(id);
    }

    @Override
    public int isHaveExam(String sid) {
        return this.baseMapper.isHaveExam(sid);
    }


    ;

}
