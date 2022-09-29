package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.mapper.ExamsMapper;
import com.ossjk.qlh.exams.service.IExamsService;
import com.ossjk.qlh.exams.vo.ExamsVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service.impl
 * @ClassName: ExamsServiceImpl
 * @Description: 考试表-服务实现类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Service
public class ExamsServiceImpl extends ServiceImpl<ExamsMapper, Exams> implements IExamsService {

    @Override
    public Page<ExamsVo> selectExamVo(Page page, QueryWrapper<Exams> queryWrapper) {
        return this.baseMapper.selectExamVo(page,queryWrapper);

    }


}
