package com.ossjk.qlh.study.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.study.entity.Attendance;
import com.ossjk.qlh.study.service.IAttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright  2022-09-27 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.study.controller
 * @ClassName: AttendanceController
 * @Description: -控制器
 * @author: flame
 * @date: 2022-09-27 16:21:34
 */
@Api(tags = "")
@RestController
@RequestMapping("/study/attendance")
public class AttendanceController extends BaseController {

    @Autowired
    private IAttendanceService iAttendanceService;

    @Autowired
    private ResourceMappersProperties ymlUri;

    @ApiOperation(value = "列表")
//	@RequiresPermissions("")
    @GetMapping("/list")
    public ResponseBean<Page<Attendance>> list(Page page,
                                               @ApiParam(value = "学生名字") @RequestParam(name = "studname", required = false) String studname,
                                               @ApiParam(value = "月分") @RequestParam(name = "value2", required = false) String value2,
                                               @ApiParam(value = "日期") @RequestParam(name = "value1", required = false) String value1) {
        QueryWrapper<Attendance> queryWrapper = new QueryWrapper<Attendance>();
		if (StrUtil.isNotBlank(studname)) {
			queryWrapper.like("studname", studname);
		}
		if (StrUtil.isNotBlank(value2)) {

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String reStr = null;
//            try {
//                Date parse2 = sdf.parse(value2);
//                Calendar rightNow = Calendar.getInstance();
//                rightNow.setTime(parse2);
//                rightNow.add(Calendar.MONTH, 1);
//                Date dt1 = rightNow.getTime();
//                reStr = sdf.format(dt1);
//                String substring = reStr.substring(0, 7);
//                reStr = substring;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            queryWrapper.eq("left(kqdate,7)", value2);
		}
		if (StrUtil.isNotBlank(value1)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String reStr = null;
//            try {
//                Date parse2 = sdf.parse(value1);
//                Calendar rightNow = Calendar.getInstance();
//                rightNow.setTime(parse2);
//                rightNow.add(Calendar.DATE, 1);
//                Date dt1 = rightNow.getTime();
//                reStr = sdf.format(dt1);
//                reStr = reStr.substring(0, 10);
//                System.out.println(reStr);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            queryWrapper.eq("kqdate", value1);
		}
		queryWrapper.orderByAsc("studname").orderByAsc("kqdate");
        Page page1 = iAttendanceService.page(page, queryWrapper);
        return ResponseBean.Success(page1);
    }

    @ApiOperation(value = "添加")
//	@RequiresPermissions("")
    @PostMapping("/save")
    public ResponseBean save(@RequestBody Attendance record) {
        if (iAttendanceService.save(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去编辑")
//	@RequiresPermissions("")
    @GetMapping("/toUpdate")
    public ResponseBean<Attendance> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        Attendance attendance = iAttendanceService.getById(id);
        if (ObjectUtil.isNotNull(attendance)) {
            return ResponseBean.Success(attendance);
        } else {
            return ResponseBean.Fail();
        }
    }


    @ApiOperation(value = "编辑")
    @RequiresPermissions("")
    @PutMapping("/update")
    public ResponseBean update(@RequestBody Attendance record) {
        if (iAttendanceService.updateById(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "删除")
    @RequiresPermissions("")
    @DeleteMapping("/remove")
    public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iAttendanceService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "去确认导入")
    @GetMapping("/toComfirm")
    public ResponseBean<List<Attendance>> toComfirm(@ApiParam(value = "fname") @RequestParam(name = "fname") String fname) {
        List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();
        Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));
        List<Attendance> eqs = iAttendanceService.parseAttendance(new File(uriMap.get("/statics/attendance"), fname));
        System.out.println(eqs);
        if (!eqs.isEmpty() && eqs.size() > 0) {
            return ResponseBean.Success(eqs);
        } else {
            return ResponseBean.Fail();
        }

    }

    @ApiOperation(value = "完成导入")
    //@RequiresPermissions("")
    @PostMapping("/toComfirm2")
    @Transactional
    public ResponseBean importDatas(@RequestBody List<Attendance> records) {
//			String sname = iAttendanceService.parseAttendance(attendance.getS);
//			question.setSbid(sname);
//			if(question.getType()<5){
//				question.setIsSubjective(2);
//			}else{
//				question.setIsSubjective(1);
//			}
        boolean b = iAttendanceService.saveBatch(records);
        if (b) {
            return ResponseBean.Success("导入成功");
        } else {
            return ResponseBean.Fail("导入失败");
        }
//		Boolean flag = iQuestionService.saveBatch(records);
//		if (flag) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}

    }
}
