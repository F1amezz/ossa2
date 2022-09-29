package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.mapper.ExamsMapper;
import com.ossjk.qlh.exams.service.IExamsService;
import com.ossjk.qlh.exams.vo.ExamsVo;
import com.ossjk.qlh.exams.vo.QuestionVO;
import com.ossjk.qlh.study.vo.FeedbackVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright  2022-07-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service.impl
 * @ClassName: ExamsServiceImpl
 * @Description: 考试表-服务实现类
 * @author: Rick.wang
 * @date:  2022-07-05 09:57:29 
 */
@Service
public class ExamsServiceImpl extends ServiceImpl<ExamsMapper, Exams> implements IExamsService {

    @Resource
    private ExamsMapper examsMapper;

    @Override
    public Page<ExamsVo> selectExamVo(Page page, QueryWrapper<Exams> queryWrapper) {
        return examsMapper.selectExamVo(page,queryWrapper);
    }

    @Override
    public String selectContent(String expid) {
        return examsMapper.selectContent(expid);
    }

    @Override
    public String selectQuestionContent(String id) {
        
        return examsMapper.selectQuestionContent(id);
    }

    @Override
    public Exampaper selectExampaperById(String id) {
        return examsMapper.selectExampaperById(id);
    }

    @Override
    public Page<ExamsVo> selectHisExamVo(Page page, QueryWrapper<Exams> queryWrapper) {
        return examsMapper.selectHisExamVo(page,queryWrapper);
    }
}
