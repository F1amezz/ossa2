package com.ossjk.qlh.stus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.mapper.StudentMapper5;
import com.ossjk.qlh.stus.service.IStudentService5;

/**
 * Copyright 2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.stus.service.impl
 * @ClassName: StudentServiceImpl
 * @Description: -服务实现类
 * @author: Rick.yang
 * @date: 2022-06-08 16:32:38
 */
@Service
public class StudentServiceImpl5 extends ServiceImpl<StudentMapper5, Student> implements IStudentService5 {

    @Override
    public List<Student> lookupClazzStu(String id) {
        return this.baseMapper.lookupClazzStu(id);
    }

    @Override
    public int updStuPwd(String deftpwd, String id) {
        return this.baseMapper.updStuPwd(deftpwd, id);
    }

    @Override
    public List<String> findClazzStuName(String cid){return this.baseMapper.findClazzStuName(cid); }
}
