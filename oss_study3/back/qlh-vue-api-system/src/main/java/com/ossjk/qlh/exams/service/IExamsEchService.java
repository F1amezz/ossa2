package com.ossjk.qlh.exams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.vo.ExamsEchVo;

import java.util.List;

public interface IExamsEchService  extends IService<ExamsEchVo> {
    ExamsEchVo selectExamsEch(String id);

    List<ExamsEchVo> selectExamsEchOne(String id);

}
