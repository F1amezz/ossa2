package com.ossjk.qlh.stus.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService10;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.stus.controller
 * @ClassName: StudentController
 * @Description: -控制器
 * @author: Rick.yang
 * @date: 2022-06-08 16:32:38
 */
@Api(tags = "学生信息")
@RestController
@RequestMapping("/stus/student")
public class StudentController11 extends BaseController {

    @Autowired
    private IStudentService10 iStudentService10;

    @Autowired
    private IClazzService iClazzService;

    @Autowired
    private ResourceMappersProperties ymlUri;

    @ApiOperation(value = "去确认导入")
    // @RequiresPermissions("")
    @GetMapping("/toImport")
    public ResponseBean<List<Student>> toImport(@ApiParam(value = "fnm") @RequestParam(name = "fnm") String fnm) {
        // 读取yuml的配置数据
        List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();
        Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(
                ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));

        List<Student> students = iStudentService10.parseStudent(new File(uriMap.get("/statics/excels"), fnm));
        if (ObjectUtil.isNotNull(students) && students.size() > 0) {
            return ResponseBean.Success(students);
        } else {
            return ResponseBean.Fail();
        }

    }

    @ApiOperation(value = "完成导入")
    // @RequiresPermissions("")
    @PutMapping("/imports")
    public ResponseBean importDatas(@RequestBody List<Student> records) {
        // name -> cid
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>();
        Clazz clz = null;
        queryWrapper.eq("name", records.get(0).getCid());
        clz = iClazzService.getOne(queryWrapper);
        if (ObjectUtil.isNull(clz)) {
            return ResponseBean.Fail("添加学生班级未创建222");
        }
        for (Student stu : records) {
            if (StrUtil.isBlank(stu.getCid())) {
                continue;
            }
            stu.setCid(clz.getId());
        }

        Boolean flag = iStudentService10.saveBatch(records);
        if (flag) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }

    }

}
