package com.ossjk.qlh.study.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.study.dto.AttendanceDTO;
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
import java.text.DateFormat;
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
    public ResponseBean<Map> list( @ApiParam(value = "学生id",required = true) @RequestParam(name = "sid", required = true) String sid,
                                   @ApiParam(value = "月份") @RequestParam(name = "mth", required = false) String mth) {
         //返回data
         Map  datas = new HashMap();

         if(StrUtil.isBlank(mth)  ){
             //默认本月   更好的逻辑是数据库中该班最大的月份
             mth = StringUtil.smt3.format(new Date());
         }

        datas.put("data",this.iAttendanceService.findStuDtoByMth(mth,sid));

         //导航的月份
          //datas.put("naviMth",this.iAttendanceService.findStuDtoByMth(mth,sid));

        return ResponseBean.Success(datas);
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

    @ApiOperation(value = "解析数据")
    @GetMapping("/toParse")
    public ResponseBean<List<AttendanceDTO>> toParse(@ApiParam(value = "fname") @RequestParam(name = "fname") String fname) {
        //取得上传地址
        List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();
        Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));
        //解析给前端回显
        List<AttendanceDTO> eqs = iAttendanceService.parseAttendance(new File(uriMap.get("/statics/attendance"), fname));
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
//        boolean b = iAttendanceService.saveBatch(records);
//        if (b) {
//            return ResponseBean.Success("导入成功");
//        } else {
//            return ResponseBean.Fail("导入失败");
//        }



        int i = 0;
        int j = 0;
        for(Attendance li : records){



//            if(ObjectUtil.isEmpty(li.getChkin()) || ObjectUtil.isEmpty(li.getChkout())){
//                li.setState("未打卡");
//            }

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





            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("stuid",li.getSid());
          //  queryWrapper.eq("studname",li.getStudname());
            queryWrapper.eq("kqdate",li.getKqdate());

            Attendance one = iAttendanceService.getOne(queryWrapper);

            if(ObjectUtil.isNotEmpty(one)){
               iAttendanceService.update(li,queryWrapper);
               i++;
            }else{
                iAttendanceService.save(li);
                j++;
            }

        }

//		Boolean flag = iQuestionService.saveBatch(records);
//		if (flag) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
        return ResponseBean.Success("成功插入"+ j +"条"+"-"+"成功修改"+ i +"条");
    }
}
