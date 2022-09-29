package com.ossjk.qlh.edu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.service.ISubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.edu.controller
 * @ClassName: SubjectController
 * @Description: Subject-控制器
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@Api(tags = "科目")
@RestController
@RequestMapping("/edu/subject")
public class SubjectController extends BaseController {

    @Autowired
    private ISubjectService iSubjectService;

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<List<Subject>> list() {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort");
        return ResponseBean.Success(iSubjectService.list(queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/listPid")
    public ResponseBean<List<Subject>> listPid() {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort").eq("pid", "0");
        return ResponseBean.Success(iSubjectService.list(queryWrapper));
    }

    @ApiOperation(value = "查询1个")
    // @RequiresPermissions("")
    @GetMapping("/getOne")
    public ResponseBean<Subject> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Subject subject = iSubjectService.getById(id);
        if (ObjectUtil.isNotNull(subject)) {
            return ResponseBean.Success(subject);
        } else {
            return ResponseBean.Fail();
        }
    }

}
