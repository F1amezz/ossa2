package com.ossjk.qlh.exams.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.vo.ExampaperVo;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service
 * @ClassName: ExampaperService
 * @Description:  试卷-服务类接口
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
public interface IExampaperService extends IService<Exampaper> {

    Page<ExampaperVo>  pageExampaperVo(Page page, QueryWrapper<ExampaperVo> queryWrapper);

}
