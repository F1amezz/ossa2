package com.ossjk.qlh.study.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.service.IFeedbackService;
import com.ossjk.qlh.study.service.ISelfevaluationService;
import com.ossjk.qlh.study.service.ISummarizeService;
import com.ossjk.qlh.study.vo.NewStatisticalChartVo;
import com.ossjk.qlh.study.vo.OneSqlStatisticalChartVo;
import com.ossjk.qlh.study.vo.StatisticalChartVo;

import com.ossjk.qlh.stus.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.study.entity.Myclass;
import com.ossjk.qlh.study.service.IMyclassService;
import com.ossjk.qlh.study.vo.MyclassVo;
import com.ossjk.qlh.stus.service.IStudentService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.controller
 * @ClassName: MyclassController
 * @Description: -控制器
 * @author: Rick.yang
 * @date: 2022-06-16 17:38:14
 */
@Api(tags = "")
@RestController
@RequestMapping("/study/myclass")
public class MyclassController extends BaseController {





    @Autowired
    private IMyclassService iMyclassService;




    @ApiOperation(value = "列出班级")
    // @RequiresPermissions("")
    @GetMapping("/listCname")
    public ResponseBean<List<Myclass>> listCname() {
        QueryWrapper<Myclass> queryWrapper = new QueryWrapper<Myclass>();
        return ResponseBean.Success(iMyclassService.list(queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<Page<MyclassVo>> list(Page page,
            @ApiParam(value = "学生名字") @RequestParam(name = "sname", required = false) String sname,
            @RequestParam(name = "cname", required = false) String cname) {
        QueryWrapper<Myclass> queryWrapper = new QueryWrapper<Myclass>();
        if (StrUtil.isNotBlank(cname)) {
            queryWrapper.eq("cname", cname);
        }
        if (StrUtil.isNotBlank(sname)) {
            queryWrapper.eq("sname", sname);
        }
        return ResponseBean.Success(iMyclassService.selectVo(page, queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list2")
    public ResponseBean<List<Myclass>> list2() {
        QueryWrapper<Myclass> queryWrapper = new QueryWrapper<Myclass>().select("distinct cname");
        return ResponseBean.Success(iMyclassService.list(queryWrapper));
    }

    @ApiOperation(value = "添加")
    // @RequiresPermissions("")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Myclass record) {
        if (iMyclassService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Myclass> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Myclass myclass = iMyclassService.getById(id);
        if (ObjectUtil.isNotNull(myclass)) {
            return ResponseBean.Success(myclass);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "编辑")
    // @RequiresPermissions("")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Myclass record) {
        if (iMyclassService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    // @RequiresPermissions("")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iMyclassService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    //////////////////////////keli修改/////////////////////////////////////////////////////////////////////////////////////////////
    @ApiOperation(value = "班级无参查询，返回第一个班级")
    // @RequiresPermissions("")
    @GetMapping("/getClassOne")
    public ResponseBean getClassOne(Page<Myclass> page) {
        page.setSize(1);
        Page<Myclass> page1 = iMyclassService.page(page);
        if (page.getRecords().size()==0){
            return ResponseBean.Fail("该表格为空");

        }
        return ResponseBean.Success(page.getRecords().stream().iterator().next());
    }



//////////////////////////克里修改2/////////////////////////////////////////////////////////////////////////////
    @ApiOperation(value = "根据cid和dateFlag，返回统计图数组")
    @GetMapping("/getSummarizeAll2")
    public ResponseBean getSummarizeAll2(@ApiParam(value = "dateFlag") @RequestParam(name = "dateFlag")int dateFlag,
                                        @ApiParam(value = "cid") @RequestParam(name = "cid")String cid) {
        ResponseBean<NewStatisticalChartVo> statisticalChart = iMyclassService.getStatisticalChart(cid, dateFlag);
        return statisticalChart;
    }


    @ApiOperation(value = "根据cid，返回学生集合")
    @GetMapping("/getStudentList")
    public ResponseBean getStudentList(@ApiParam(value = "cid") @RequestParam(name = "cid")String cid) {
        ResponseBean<List<Student>> studentList = iMyclassService.getStudentList(cid);
        return studentList;
    }




}
