package com.ossjk.qlh.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.vo.FeedbackVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright  2022-07-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study.service
 * @ClassName: FeedbackService
 * @Description:  总结表-服务类接口
 * @author: Rick.yang
 * @date:  2022-07-05 16:48:09 
 */
public interface IFeedbackService extends IService<Feedback> {


    Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> qw);

    List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> qw);

//    List<Feedback> interaSname(String sname);
    FeedbackVo selectVoById(String id);

    int isHaveExam( String sid);

}
