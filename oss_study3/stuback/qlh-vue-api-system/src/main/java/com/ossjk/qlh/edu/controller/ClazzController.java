package com.ossjk.qlh.edu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.controller
 * @ClassName: ClazzController
 * @Description: Clazz-控制器
 * @author: Rick.yang
 * @date: 2022-06-08 19:34:03
 */
@Api(tags = "班级表")
@RestController
@RequestMapping("/edu/clazz")
public class ClazzController extends BaseController {

    @Autowired
    private IClazzService iClazzService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ISystemCommonApi iSystemCommonApi;

    @ApiOperation(value = "列表")
    @RequiresPermissions("003000150020")
    @GetMapping("/list")
    public ResponseBean<Page<Clazz>> list(Page page, HttpServletRequest request, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>();
        if (StrUtil.isNotBlank(clazz.getName()))
            queryWrapper.like("name", "%" + clazz.getName() + "%");
        if (StrUtil.isNotBlank(clazz.getTypes()))
            queryWrapper.like("types", "%" + clazz.getTypes() + "%");
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String roleStr = me.getRoleCode().iterator().next();
        Page<Clazz> clzes = null;
        // 教学总监 / 班主任
        if ("mgr2".equals(roleStr) || "xz".equals(roleStr) || "admin".equals(roleStr)) {
            clzes = iClazzService.pageVO(page, queryWrapper);
        } else if ("tea".equals(roleStr)) {
            System.out.println(1);
            queryWrapper.eq("tid", me.getId());
            clzes = iClazzService.pageVO(page, queryWrapper);
        } else {
            clzes = iClazzService.pageVOByTchrID(page, me.getId(), queryWrapper);
        }
        System.out.println(me.getId());
        // 老师
        return ResponseBean.Success(clzes);
    }

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list2")
    public ResponseBean<Page<Clazz>> list2(Page page, HttpServletRequest request) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>();
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String roleStr = me.getRoleCode().iterator().next();
        Page<Clazz> clzes = null;
        // 教学总监 / 班主任
        if ("mgr2".equals(roleStr) || "xz".equals(roleStr) || "admin".equals(roleStr)) {
            clzes = iClazzService.pageVO(page, queryWrapper);
        } else {
            clzes = iClazzService.pageVOByTchrID(page, me.getId(), queryWrapper);
        }
        return ResponseBean.Success(clzes);
    }

    @ApiOperation(value = "列出学生")
    // @RequiresPermissions("")
    @GetMapping("/student")
    public ResponseBean<Map<String, Object>> listStudent(@ApiParam(value = "班级主键") String cid) {
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isNullOrUndefined(cid)) {
            return ResponseBean.Success(map);
        }
        Clazz clazz = iClazzService.getById(cid);
        map.put("clazz", clazz);
        return ResponseBean.Success(map);
    }

    @ApiOperation(value = "列出学生")
    // @RequiresPermissions("")
    @GetMapping("/student2")
    public ResponseBean<Map<String, Object>> listStudent2(@ApiParam(value = "班级主键") String sid) {
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isNullOrUndefined(sid)) {
            return ResponseBean.Success(map);
        }
        Student student = iStudentService.getById(sid);
        Clazz clazz = iClazzService.getById(student.getCid());
        map.put("clazz", clazz);
        return ResponseBean.Success(map);
    }

    @ApiOperation(value = "模糊查询")
    @RequiresPermissions("003000150030")
    @GetMapping("/fuzzyQuery")
    public ResponseBean<Page<Clazz>> fuzzyQuery(Page page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>();
        if (StrUtil.isNotBlank(clazz.getName())) {
            queryWrapper.like("c.name", clazz.getName());
        }
        if (StrUtil.isNotBlank(clazz.getTypes())) {
            queryWrapper.like("types", clazz.getTypes());
        }
        Page<Clazz> clazzList = iClazzService.pageVO(page, queryWrapper);
        return ResponseBean.Success(clazzList);
    }

    @ApiOperation(value = "添加")
    @RequiresPermissions("003000150005")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Clazz record) {
        if (iClazzService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Clazz> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Clazz clazz = iClazzService.getById(id);
        if (ObjectUtil.isNotNull(clazz)) {
            return ResponseBean.Success(clazz);
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "修改")
    @RequiresPermissions("003000150010")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Clazz record) {
        System.out.println(record.toString());
        if (iClazzService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    @RequiresPermissions("003000150015")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iClazzService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }



}
