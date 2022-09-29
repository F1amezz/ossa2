package com.ossjk.qlh.study.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.service.ISelfevaluationService;
import com.ossjk.qlh.study.vo.SelfevaluationVo;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.xuexi.controller
 * @ClassName: SelfevaluationController
 * @Description: Selfevaluation-控制器
 * @author: Rick.yang
 * @date: 2022-06-14 17:12:17
 */
@Api(tags = "自我评价表")
@RestController
@RequestMapping("/study/selfevaluation")
public class SelfevaluationController extends BaseController {

    @Autowired
    private ISelfevaluationService iSelfevaluationService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IClazzService iClazzService;

    @Autowired
    private ISystemCommonApi iSystemCommonApi;

    @ApiOperation(value = "列表")
    // @RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<Page<SelfevaluationVo>> list(Page page, SelfevaluationVo selfevaluationVo,
            HttpServletRequest request) {
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String roleStr = me.getRoleCode().iterator().next();
        QueryWrapper<SelfevaluationVo> queryWrapper = new QueryWrapper<SelfevaluationVo>().orderByAsc("`readst`");
        if (StrUtil.isNotBlank(selfevaluationVo.getSname())) {
            queryWrapper.like("sname", "%" + selfevaluationVo.getSname() + "%");
        }
        if (StrUtil.isNotBlank(selfevaluationVo.getCname())) {
            queryWrapper.eq("cname", selfevaluationVo.getCname());
        }
        System.out.println(selfevaluationVo.getReadst());
        if (StrUtil.isNotBlank(selfevaluationVo.getReadst())) {
            queryWrapper.eq("readst", selfevaluationVo.getReadst());
        }
        if ("tea".equals(roleStr)) {
            queryWrapper.eq("tname", me.getName());
        }
        return ResponseBean.Success(iSelfevaluationService.selectVo(page, queryWrapper));
    }

    @ApiOperation(value = "列出班级")
    // @RequiresPermissions("")
    @GetMapping("/listCname")
    public ResponseBean<Map<String, Object>> listCname(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>().select("DISTINCT id,`name`");
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String roleStr = me.getRoleCode().iterator().next();
        if ("tea".equals(roleStr)) {
            queryWrapper.eq("tid", me.getId());
        }
        List<Clazz> clazzList = iClazzService.list(queryWrapper);
        map.put("clazzList", clazzList);
        return ResponseBean.Success(map);
    }

    @ApiOperation(value = "添加")
     @RequiresPermissions("00223001")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Selfevaluation record) {
        SaveOrUpdate(record);
        if (iSelfevaluationService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    private void SaveOrUpdate(@RequestBody Selfevaluation record) {
        Student student = iStudentService.getById(record.getSid());
        Clazz clazz = iClazzService.getById(student.getCid());
        record.setCid(clazz.getId());
        record.setTid(clazz.getTid());
    }

    @ApiOperation(value = "去编辑")
    // @RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Page<SelfevaluationVo>> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id,
            Page page) {
        System.out.println(id);
        QueryWrapper<SelfevaluationVo> wrapper = new QueryWrapper<SelfevaluationVo>().eq("id", id);
        Page<SelfevaluationVo> list = iSelfevaluationService.selectVo(page, wrapper);
        return ResponseBean.Success(list);
    }

    @ApiOperation(value = "编辑")
     @RequiresPermissions("00223005")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Selfevaluation record) {
        SaveOrUpdate(record);
        if (iSelfevaluationService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
     @RequiresPermissions("00223006")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iSelfevaluationService.removeByIds(Arrays.asList(ids))) {
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
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        UpdateWrapper<Selfevaluation> updateWrapper = new UpdateWrapper<Selfevaluation>().in("id", ids).set("readst", 1)
                .set("readtime", time);
        ;
        boolean f = iSelfevaluationService.update(updateWrapper);
        if (f) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }
}
