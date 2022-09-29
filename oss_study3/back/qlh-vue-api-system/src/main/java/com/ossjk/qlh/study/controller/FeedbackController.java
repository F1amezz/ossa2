package com.ossjk.qlh.study.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.service.IFeedbackService;
import com.ossjk.qlh.study.vo.FeedbackVo;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.controller
 * @ClassName: FeedbackController
 * @Description: Feedback-控制器
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
@Api(tags = "总结反馈表")
@RestController
@RequestMapping("/study/feedback")
public class FeedbackController extends BaseController {
    @Autowired
    private IFeedbackService iFeedbackService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IClazzService iClazzService;

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<Page<FeedbackVo>> list(Page page, FeedbackVo feedbackVo) {
        QueryWrapper<FeedbackVo> queryWrapper = new QueryWrapper<FeedbackVo>().orderByDesc("subtime");
        if (StrUtil.isNotBlank(feedbackVo.getSname())) {
            queryWrapper.like("sname", "%" + feedbackVo.getSname() + "%");
        }
        if (StrUtil.isNotBlank(feedbackVo.getCname())) {
            queryWrapper.eq("cname", feedbackVo.getCname());
        }
        System.out.println(feedbackVo.getReadst());
        if (StrUtil.isNotBlank(feedbackVo.getReadst())) {
            queryWrapper.eq("readst", feedbackVo.getReadst());
        }
        return ResponseBean.Success(iFeedbackService.selectVo(page, queryWrapper));
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list2")
    public ResponseBean<List<FeedbackVo>> list2(Page page, FeedbackVo feedbackVo) {
        QueryWrapper<FeedbackVo> queryWrapper = new QueryWrapper<FeedbackVo>();

        if (StrUtil.isNotBlank(feedbackVo.getCname())) {
            queryWrapper.eq("c.name", feedbackVo.getCname());
        }
        return ResponseBean.Success(iFeedbackService.selectVoList(queryWrapper));
    }

    @ApiOperation(value = "列出班级")
    // @RequiresPermissions("")
    @GetMapping("/listCname")
    public ResponseBean<Map<String, Object>> listCname() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>().select("DISTINCT id,`name`");
        List<Clazz> clazzList = iClazzService.list(queryWrapper);
        map.put("clazzList", clazzList);
        return ResponseBean.Success(map);
    }

    private void SaveOrUpdate(@RequestBody FeedbackVo record) {
        Student student = iStudentService.getById(record.getSid());
        Clazz clazz = iClazzService.getById(student.getCid());
        record.setCid(clazz.getId());
        record.setCourseid(clazz.getCid());
        record.setTid(clazz.getTid());
    }

    @ApiOperation(value = "添加")
    // @RequiresPermissions("")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody FeedbackVo record, MultipartFile f) throws IOException {
        // InputStream in = f.getInputStream();
        // byte[] buffer = new byte[in.available()];
        // in.read(buffer);
        // in.close();
        // // data:application/pdf;base64, 是图片前缀，必须加，不然无法解析
        // String rel = "data:application/pdf;base64," +
        // Base64.getEncoder().encodeToString(buffer);

        SaveOrUpdate(record);
        if (iFeedbackService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<FeedbackVo> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        if (ObjectUtil.isNotNull(id)) {
            return ResponseBean.Success(iFeedbackService.selectVoById(id));
        } else {
            return ResponseBean.Fail();
        }
    }

    private void SaveOrUpdate(@RequestBody Feedback record) {
        Student student = iStudentService.getById(record.getSid());
        Clazz clazz = iClazzService.getById(student.getCid());
        record.setCid(clazz.getId());
        record.setCourseid(clazz.getCid());
        record.setTid(clazz.getTid());
    }

    @ApiOperation(value = "编辑")
    // @RequiresPermissions("")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody FeedbackVo record) {
        SaveOrUpdate(record);
        if (iFeedbackService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    // @RequiresPermissions("")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iFeedbackService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    /**
     * 批量修改已读状态为已读 0未读1已读
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "修改已读状态为已读")
    // @RequiresPermissions("")
    @DeleteMapping("/updateReadst")
    public ResponseBean updateReadst(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        UpdateWrapper<Feedback> updateWrapper = new UpdateWrapper<Feedback>().in("id", ids).set("readst", 1)
                .set("chetime", time);
        ;
        boolean f = iFeedbackService.update(updateWrapper);
        if (f) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    /**
     * 修改已读状态为已读 0未读1已读
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "修改已读状态为已读")
    // @RequiresPermissions("")
    @DeleteMapping("/updateReadst2")
    public ResponseBean updateReadst2(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        UpdateWrapper<Feedback> updateWrapper = new UpdateWrapper<Feedback>().eq("id", id).set("readst", 1)
                .set("chetime", time);
        boolean f = iFeedbackService.update(updateWrapper);
        if (f) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }
}
