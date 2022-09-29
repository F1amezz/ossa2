package com.ossjk.qlh.exams.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.vo.QuestionSubVo;

import java.io.File;
import java.util.List;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service
 * @ClassName: QuestionService
 * @Description:  试题表-服务类接口
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
public interface IQuestionService extends IService<Question> {


    Page<QuestionSubVo> selectQuSubVo(Page page, QueryWrapper<Question> queryWrapper);
    List<Question> parseQuestion(File fname);

}
