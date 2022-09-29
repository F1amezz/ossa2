package com.ossjk.qlh.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.dto.StudentDto;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.vo.StudentVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: UserService
 * @Description: 用户表-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
public interface IStudentService extends IService<Student> {

    boolean saveDto(StudentDto dto);

    boolean updateDtoById(StudentDto dto);

    Page pageVo(Page page, Wrapper wrapper);

    StudentVo getVoById(Serializable id);



}
