package com.ossjk.qlh.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.vo.FeedbackVo;

import java.time.LocalDate;
import java.util.List;

/**
 * Copyright  2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service
 * @ClassName: FeedbackService
 * @Description:  总结反馈表-服务类接口
 * @author: Rick.yang
 * @date:  2022-06-13 18:03:21 
 */
public interface IFeedbackService extends IService<Feedback> {
    Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> qw);

    List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> qw);

    FeedbackVo selectVoById(String id);


}
