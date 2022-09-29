package com.ossjk.qlh.exams.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.edu.vo.ClazzVO;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.vo.ExamsVo;
import com.ossjk.qlh.exams.vo.QuestionSubVo;

import java.util.List;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service
 * @ClassName: ExamsService
 * @Description:  考试表-服务类接口
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
public interface IExamsService extends IService<Exams> {
    Page<ExamsVo>  selectExamVo(Page page, QueryWrapper<Exams> queryWrapper);


}
