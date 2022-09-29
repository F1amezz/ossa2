package com.ossjk.qlh.study.service.impl;



import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.mapper.SelfevaluationMapper;
import com.ossjk.qlh.study.service.ISelfevaluationService;
import com.ossjk.qlh.study.vo.SelfevaluationVo;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private IClazzService iClazzService;
    @Autowired
    private IStudentService iStudentService;

    @Override
    public Page<SelfevaluationVo> selectVo(Page page, QueryWrapper<SelfevaluationVo> qw) {
        return this.baseMapper.selectVo(page, qw);
    }



}
