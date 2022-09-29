package com.ossjk.qlh.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.study.entity.Myclass;
import com.ossjk.qlh.study.vo.MyclassVo;
import com.ossjk.qlh.study.vo.NewStatisticalChartVo;
import com.ossjk.qlh.study.vo.OneSqlStatisticalChartVo;
import com.ossjk.qlh.stus.entity.Student;

import java.time.LocalDate;
import java.util.List;

/**
 * Copyright  2022-06-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service
 * @ClassName: MyclassService
 * @Description:  -服务类接口
 * @author: Rick.yang
 * @date:  2022-06-16 17:38:14 
 */
public interface IMyclassService extends IService<Myclass> {
    Page<MyclassVo> selectVo(Page page, QueryWrapper<Myclass> qw);

    public ResponseBean<NewStatisticalChartVo> getSummarizeAll2(String cid, int dateFlag);
    public ResponseBean<List<Student>> getStudentList(String cid);
    public ResponseBean<NewStatisticalChartVo> getStatisticalChart(String cid, int dateFlag);
}
