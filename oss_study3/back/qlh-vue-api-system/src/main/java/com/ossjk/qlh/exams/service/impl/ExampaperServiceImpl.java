package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.mapper.ExampaperMapper;
import com.ossjk.qlh.exams.service.IExampaperService;
import com.ossjk.qlh.exams.vo.ExampaperVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service.impl
 * @ClassName: ExampaperServiceImpl
 * @Description: 试卷-服务实现类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Service
public class ExampaperServiceImpl extends ServiceImpl<ExampaperMapper, Exampaper> implements IExampaperService {

    @Resource
    private ExampaperMapper exampaperMapper;

    @Override
    public Page<ExampaperVo> pageExampaperVo(Page page, QueryWrapper<ExampaperVo> queryWrapper) {
        return exampaperMapper.pageExampaperVo(page,queryWrapper);
    }
}
