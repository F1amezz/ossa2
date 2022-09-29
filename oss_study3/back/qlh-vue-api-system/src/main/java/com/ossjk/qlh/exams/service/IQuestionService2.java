package com.ossjk.qlh.exams.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.vo.QuestionExampVo;

import java.util.List;

public interface IQuestionService2 extends IService<Question> {

    Page<QuestionExampVo> selectQuSubVo(Page page, QueryWrapper<Question> queryWrapper);

    List<Question> listData(String[] quIds);
}
