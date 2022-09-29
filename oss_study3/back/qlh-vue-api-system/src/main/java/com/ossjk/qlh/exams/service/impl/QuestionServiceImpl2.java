package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.mapper.QuestionMapper2;
import com.ossjk.qlh.exams.service.IQuestionService2;
import com.ossjk.qlh.exams.vo.QuestionExampVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceImpl2 extends ServiceImpl<QuestionMapper2, Question> implements IQuestionService2 {
    @Override
    public Page<QuestionExampVo> selectQuSubVo(Page page, QueryWrapper<Question> queryWrapper) {
        return this.baseMapper.selectQuSubVo(page,queryWrapper);
    }

    @Override
    public List<Question> listData(String[] quIds) {



        return this.baseMapper.selectBatchIds(Arrays.asList(quIds));
    }
}
