package com.ossjk.qlh.edu.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
    public ResponseBean<Page<Clazz>> list(Page page, HttpServletRequest request, Clazz clazz)  {

        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<Clazz>();

        if (StrUtil.isNotBlank(clazz.getName())) {
            queryWrapper.like("name", "%" + clazz.getName() + "%");
        }
        if (StrUtil.isNotBlank(clazz.getTypes())) {
            queryWrapper.like("types", "%" + clazz.getTypes() + "%");
        }
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

    @ApiOperation(value = "导出全部快速查分")
    // 点击下载excel 表格
    @RequestMapping(value = "/exportScore2", method = RequestMethod.GET)
    public void downAll2(@ApiParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        String[] split = ids.split(",");
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>().in("cid", split);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<Student> classmateList = iStudentService.list(studentQueryWrapper);
        // 设置要导出的文件的名字
        String fileName = "学生信息表" + ".xls";
        // 新增数据行，并且设置单元格数据
        // 设置一行（第一行）
        HSSFRow row3 = null;
        row3 = sheet.createRow(0); // 创建第一个单元格
        row3.setHeight((short) (26.5 * 20)); // 设置行高，在这里26.5就是这一行的行高
        if (split.length == 1) {
            row3.createCell(0).setCellValue(iClazzService.getById(split[0]).getName() + "班级学生信息"); // 这个单元格的值
        } else {
            row3.createCell(0).setCellValue("班级学生信息"); // 这个单元格的值
        }
        int rowNum = 2;
        String[] headers = { "姓名", "地址", "学校名称", "所学专业", "学生手机号", "登录名" };
        // headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(1);
        // 设置行高
        row.setHeight((short) (22.50 * 20));
        // 在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 在表中存放查询到的数据放入对应的列
        for (Student table : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            // 设置行高
            row1.setHeight((short) (22.50 * 20));
            rowNum = getRowNum(rowNum, table, row1);
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        // 列宽自适应
        for (int i = 0; i <= 5; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public static int getRowNum(int rowNum, Student table, HSSFRow row1) {
        row1.createCell(0).setCellValue(table.getName());
        row1.createCell(1).setCellValue(table.getAdress());
        row1.createCell(2).setCellValue(table.getSchool());
        row1.createCell(3).setCellValue(table.getProfessional());
        row1.createCell(4).setCellValue(table.getMobile());
        row1.createCell(5).setCellValue(table.getLname());
        rowNum++;
        return rowNum;
    }

}
