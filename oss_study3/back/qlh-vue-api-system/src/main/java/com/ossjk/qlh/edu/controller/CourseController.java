package com.ossjk.qlh.edu.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Course;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.service.ICourseService;
import com.ossjk.qlh.edu.service.ISubjectService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.controller
 * @ClassName: CourseController
 * @Description: Course-控制器
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
@Api(tags = "课程表")
@RestController
@RequestMapping("/edu/course")
public class CourseController extends BaseController {

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private ISubjectService iSubjectService;

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<Page<Course>> list(Page page, Course course) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>().orderByDesc("crtm");
        if (course.getType() != null && course.getType() != 0) {
            queryWrapper.eq("type", course.getType());
        }
        if (StrUtil.isNotBlank(course.getName())) {
            queryWrapper.like("name", "%" + course.getName() + "%");
        }
        if (StrUtil.isNotBlank(course.getVersion())) {
            queryWrapper.like("version", "%" + course.getVersion() + "%");
        }

        queryWrapper.orderByDesc("year");

        return ResponseBean.Success(iCourseService.page(page, queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list2")
    public ResponseBean<List<Course>> list(Course course) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.select("name,version");
        if (StrUtil.isNotBlank(course.getName())) {
            queryWrapper.like("name", "%" + course.getName() + "%");
        }
        if (StrUtil.isNotBlank(course.getVersion())) {
            queryWrapper.like("version", "%" + course.getVersion() + "%");
        }
        return ResponseBean.Success(iCourseService.list(queryWrapper));
    }

    @ApiOperation(value = "添加")
    // @RequiresPermissions("")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Course record) {
        saveOrUpdate(record);
        boolean f = iCourseService.save(record);
        if (f) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "编辑")
    // @RequiresPermissions("")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Course record) {
        saveOrUpdate(record);
        if (iCourseService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    private void saveOrUpdate(@RequestBody Course record) {
        String substring = record.getSid().replaceAll("\"", "");
        String s = substring.substring(1, substring.length() - 1);
        record.setSid(s);
        String[] split = s.split(",");
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().in("id", split);
        queryWrapper.select("ifnull(sum(days),0) as days");
        Subject one = iSubjectService.getOne(queryWrapper);
        record.setDays(one.getDays());
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Map<String, Object>> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Course course = iCourseService.getById(id);
        String[] ids = course.getSid().split(",");
        // List<Subject> subjects = iSubjectService.listByIds(Arrays.asList(ids));
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort").eq("pid", "0");
        List<Subject> subjectAll = iSubjectService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("subjects", ids);
        map.put("subjectsAll", subjectAll);
        map.put("course", course);
        if (ObjectUtil.isNotNull(course)) {
            return ResponseBean.Success(map);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑Service")
    // @RequiresPermissions("")
    @GetMapping("/toUpdateSubject")
    public ResponseBean<Map<String, Object>> toUpdateService(
            @ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Course course = iCourseService.getById(id);
        String[] ids = course.getSid().split(",");
        // List<Subject> subjects = iSubjectService.listByIds(Arrays.asList(ids));
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort").eq("pid", "0");
        List<Subject> subjectAll = iSubjectService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("subjects", ids);
        map.put("subjectsAll", subjectAll);
        if (ObjectUtil.isNotNull(course)) {
            return ResponseBean.Success(map);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去添加页面")
    // @RequiresPermissions("")
    @GetMapping("/toSaveSubject")
    public ResponseBean<Map<String, Object>> toSaveService() {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort").eq("pid", "0");
        List<Subject> subjectAll = iSubjectService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectsAll", subjectAll);
        return ResponseBean.Success(map);
    }

    @ApiOperation(value = "去查看Subject")
    // @RequiresPermissions("")
    @GetMapping("/toUpdateSubject2")
    public ResponseBean<Map<String, Object>> toUpdateService2(
            @ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Course course = iCourseService.getById(id);
        String[] ids = course.getSid().split(",");
        // List<Subject> subjects = iSubjectService.listByIds(Arrays.asList(ids));
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<Subject>().orderByAsc("level", "sort").in("pid", ids).or()
                .in("id", ids);
        List<Subject> subjectAll = iSubjectService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        // map.put("subjects", subjects);
        map.put("subjectsAll", subjectAll);
        if (ObjectUtil.isNotNull(course)) {
            return ResponseBean.Success(map);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "编辑")
    // @RequiresPermissions("")
    @PutMapping("/update2")
    public ResponseBean update2(@RequestBody Course record) {
        String sid = record.getSid().replaceAll("\"", "");
        String substring = sid.substring(1, sid.length() - 1);
        record.setSid(substring);
        if (iCourseService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    // @RequiresPermissions("")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iCourseService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }
}
