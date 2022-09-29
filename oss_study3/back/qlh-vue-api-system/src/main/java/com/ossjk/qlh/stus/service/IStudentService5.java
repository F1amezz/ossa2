package com.ossjk.qlh.stus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.vo.StudentVO;

import java.util.List;

/**
 * Copyright  2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.stus.service
 * @ClassName: StudentService
 * @Description:  -服务类接口
 * @author: Rick.yang
 * @date:  2022-06-08 16:32:38 
 */
public interface IStudentService5 extends IService<Student> {

    List<Student> lookupClazzStu(String id);

    int updStuPwd(String deftpwd,String id);

    List<String> findClazzStuName(String cid);
}
