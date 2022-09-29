package com.ossjk.qlh.stus.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.mapper.StudentMapper10;
import com.ossjk.qlh.stus.service.IStudentService10;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

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
public class StudentServiceImpl10 extends ServiceImpl<StudentMapper10, Student> implements IStudentService10 {

    @Override
    public List<Student> lookupClazzStu(String cid) {
        return this.baseMapper.lookupClazzStu(cid);
    }

    @Override
    public List<Student> parseStudent(File fname) {
        // 解析excel文件
        try {
            FileInputStream in = new FileInputStream(fname);
            List<Student> list = ExcelImportUtil.importExcel(in, Student.class, new ImportParams());
            in.close();
            fname.delete();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
