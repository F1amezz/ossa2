package com.ossjk.qlh.study.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.vo.SelfevaluationVo;
import com.ossjk.qlh.system.entity.Student;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service
 * @ClassName: SelfevaluationService
 * @Description: 自我评价表-服务类接口
 * @author: Rick.yang
 * @date: 2022-06-14 17:12:17
 */
public interface ISelfevaluationService extends IService<Selfevaluation> {

    Page<SelfevaluationVo> selectVo(Page page, QueryWrapper<SelfevaluationVo> qw);



}
