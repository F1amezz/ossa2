package com.ossjk.qlh.edu.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.mapper.SubjectMapper;
import com.ossjk.qlh.edu.service.ISubjectService;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.service.impl
 * @ClassName: SubjectServiceImpl
 * @Description: 科目表-服务实现类
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {
    @Override
    public String parseSidBySname(String sname) {
        return this.baseMapper.parseSidBySname(sname);
    }

}
