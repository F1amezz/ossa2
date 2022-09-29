package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Course;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.service.ICourseService;
import com.ossjk.qlh.edu.service.ISubjectService;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.service.IExampaperService;
import com.ossjk.qlh.exams.vo.ExampaperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: ExampaperController
 * @Description: Exampaper-控制器
 * @author:
 * @date: 2022-06-14 11:31:17
 */
@Api(tags = "试卷")
@RestController
@RequestMapping("/exams/exampaper")
public class ExampaperController extends BaseController {

    @Autowired
    private IExampaperService iExampaperService;
    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private ISubjectService iSubjectService;

    @ApiOperation(value = "列表")
    @RequiresPermissions("001600005")
    @GetMapping("/list")
    public ResponseBean<Page<ExampaperVo>> list(Page page,
                                                @ApiParam(value = "创建人名称") @RequestParam(name = "crerName", required = false) String crerName,
                                                @ApiParam(value = "试卷名称") @RequestParam(name = "name", required = false) String name) {
        QueryWrapper<ExampaperVo> queryWrapper = new QueryWrapper<ExampaperVo>();
        if (StrUtil.isNotBlank(crerName)) {
            queryWrapper.like("user.name", crerName);
        }
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("exampaper.name", name);
        }

        //根据创建时间降序排序
        queryWrapper.orderByDesc("crtm");
        return ResponseBean.Success(iExampaperService.pageExampaperVo(page, queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list2")
    public ResponseBean<List<Exampaper>> list(Exampaper page) {
        QueryWrapper<Exampaper> queryWrapper = new QueryWrapper<Exampaper>().eq("kid", page.getKid());
        return ResponseBean.Success(iExampaperService.list(queryWrapper));
    }

    /**
     * 查找启用的试卷
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "列表")
//    @RequiresPermissions("")
    @GetMapping("/list3")
    public ResponseBean<Page<Exampaper>> list3(Page page) {
        QueryWrapper<Exampaper> queryWrapper = new QueryWrapper<Exampaper>();
        queryWrapper.eq("status", "1");
        //根据创建时间降序排序
        queryWrapper.orderByDesc("crtm");
        return ResponseBean.Success(iExampaperService.page(page, queryWrapper));
    }

    @ApiOperation(value = "添加")
    @RequiresPermissions("001600005005")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Exampaper record) {
        if (iExampaperService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑")
    @RequiresPermissions("001600005010")
    @GetMapping("/toUpdate")
    public ResponseBean<Exampaper> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Exampaper exampaper = iExampaperService.getById(id);
        if (ObjectUtil.isNotNull(exampaper)) {
            return ResponseBean.Success(exampaper);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "编辑")
    @RequiresPermissions("001600005010")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Exampaper record) {
        if (iExampaperService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    @RequiresPermissions("001600005015")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iExampaperService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    /**
     * 查询课程
     */
    @ApiOperation(value = "查询课程")
    // @RequiresPermissions("")
    @GetMapping("/listCourse")
    public ResponseBean<List<Course>> listCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //根据创建时间降序排序
        queryWrapper.orderByDesc("crtm");
        return ResponseBean.Success(iCourseService.list(queryWrapper));
    }

    @ApiOperation(value = "查询模块")
    // @RequiresPermissions("")
    @GetMapping("/listMid")
    public ResponseBean<List<Subject>> listMid(@RequestParam(name ="mid", required = false) String[] mid) {
        return ResponseBean.Success(iSubjectService.listByIds(Arrays.asList(mid)));
    }

    @ApiOperation(value = "查询科目")
    // @RequiresPermissions("")
    @GetMapping("/listKid")
    public ResponseBean<List<Subject>> listKid(@RequestParam(name ="mid", required = false)  String mid) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",mid);
        return ResponseBean.Success(iSubjectService.list(queryWrapper));
    }

}
