package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.mapper.ExamsEchMapper;
import com.ossjk.qlh.exams.service.IExamsEchService;
import com.ossjk.qlh.exams.vo.ExamsEchVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExamsEchServiceImpl extends ServiceImpl<ExamsEchMapper, ExamsEchVo> implements IExamsEchService {

    @Resource
    private ExamsEchMapper examsEchMapper;

    @Override
    public ExamsEchVo selectExamsEch(String id) {
        return examsEchMapper.selectExamsEch(id);
    }

    @Override
    public List<ExamsEchVo> selectExamsEchOne(String id) {
        return examsEchMapper.selectExamsEchOne(id);
    }


}
