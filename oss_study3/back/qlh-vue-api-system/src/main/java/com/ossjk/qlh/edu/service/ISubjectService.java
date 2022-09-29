package com.ossjk.qlh.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.edu.entity.Subject;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.service
 * @ClassName: SubjectService
 * @Description: 科目表-服务类接口
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
public interface ISubjectService extends IService<Subject> {
    String parseSidBySname(String sname);

}
