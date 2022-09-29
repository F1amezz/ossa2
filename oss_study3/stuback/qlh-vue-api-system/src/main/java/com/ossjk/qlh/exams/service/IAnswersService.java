package com.ossjk.qlh.exams.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.exams.entity.Answers;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 答卷（题）表 服务类
 * </p>
 *
 * @author admin
 * @since 2022-08-01
 */
public interface IAnswersService extends IService<Answers> {
    Boolean selectExamInTime(  String sid,   Date nowTm);
}
