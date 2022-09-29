package com.ossjk.qlh.edu.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.service.ISubjectService;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.edu.controller
 * @ClassName: SubjectController
 * @Description: Subject-控制器
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@Api(tags = "科目表")
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

    @ApiOperation(value = "添加")
    // @RequiresPermissions("")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Subject record) {
        boolean f = iSubjectService.save(record);
        if (f && saveOrUpdate(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    private boolean saveOrUpdate(Subject record) {
        if (record.getPid().equals("0"))
            return true;
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().eq("pid", record.getPid());
        queryWrapper.select("ifnull(sum(days),0) as days");
        Subject one = iSubjectService.getOne(queryWrapper);
        UpdateWrapper<Subject> eq = new UpdateWrapper<Subject>().set("days", one.getDays()).eq("id", record.getPid());
        return iSubjectService.update(eq);
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Subject> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Subject subject = iSubjectService.getById(id);
        if (ObjectUtil.isNotNull(subject)) {
            return ResponseBean.Success(subject);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "编辑")
    // @RequiresPermissions("")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Subject record) {
        if (iSubjectService.updateById(record) && saveOrUpdate(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    // @RequiresPermissions("")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iSubjectService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }
}
