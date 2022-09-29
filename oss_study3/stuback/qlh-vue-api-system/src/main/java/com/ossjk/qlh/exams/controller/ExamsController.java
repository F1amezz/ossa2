package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.entity.Answers;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.service.IAnswersService;
import com.ossjk.qlh.exams.service.IExamsService;
import com.ossjk.qlh.exams.vo.ExamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright  2022-07-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: ExamsController
 * @Description: Exams-控制器
 * @author: Rick.wang
 * @date:  2022-07-05 09:57:29 
 */
@Api(tags = "考试表")
@RestController
@RequestMapping("/exams/exams")
public class ExamsController extends BaseController {
	
	@Autowired
	private IExamsService iExamsService;
	@Autowired
	private ISystemCommonApi systemCommonApi;
	@Autowired
	private IAnswersService iAnswersService;
	@Autowired
	private Date date;

	@ApiOperation(value = "列表")
//	@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<List<ExamsVo>> list(Page page,HttpServletRequest request,
											@ApiParam(value = "试卷类型") @RequestParam(name = "extype",required = false) Integer extype,
											@ApiParam(value = "试卷名称") @RequestParam(name = "name",required = false) String name) {

//		查看自己的考试
		String token = this.getToekn(request);

		LoginUser uer = systemCommonApi.getCacheLoginUser(token);

		QueryWrapper<Exams> queryWrapper = new QueryWrapper<Exams>();
		queryWrapper.eq("student.id",uer.getId());
		if(StrUtil.isNotBlank(name)){
			queryWrapper.like("exams.name",name);
		}
		if(extype!=null){
			queryWrapper.eq("exampaper.type",extype);
		}
		queryWrapper.apply("exams.end_time > now()");
		queryWrapper.like("exams.sid",'"'+uer.getName()+'"');
//		降序
		queryWrapper.orderByDesc("crtm");
		Page<ExamsVo> examsVoPage = iExamsService.selectExamVo(page, queryWrapper);

		/////////////////////////keli/////////////////////////
		QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
		answersQueryWrapper.eq("sid",uer.getId());
		List<Answers> list = iAnswersService.list(answersQueryWrapper);
		List<ExamsVo> records = examsVoPage.getRecords();
		Iterator<ExamsVo> iterator = records.stream().iterator();
		//开始考试状态赋值
		while (iterator.hasNext()){
			ExamsVo next = iterator.next();
			if (next.getEndTime().compareTo(new Date())==-1){
				next.setState(1);
			}
			Iterator<Answers> iterator1 = list.iterator();
			while (iterator1.hasNext()){
				Answers next1 = iterator1.next();
				if (next1.getKid().equals(next.getId())&&(System.currentTimeMillis()-next1.getStartexam())>(next.getExduration()*60*1000)){
						next.setState(1);
				}
			}
		}
		//历史记录状态赋值
		Iterator<ExamsVo> iterator2 = records.stream().iterator();
		while (iterator2.hasNext()){
			ExamsVo next = iterator2.next();
			Iterator<Answers> iterator1 = list.iterator();
			while (iterator1.hasNext()){
				Answers next1 = iterator1.next();
				if (next1.getKid().equals(next.getId())&&next1.getAnswer()!=null){
					next.setLookState(1);
				}
			}
		}




		return ResponseBean.Success(records);
	}

	@ApiOperation(value = "列表")
//	@RequiresPermissions("")
	@GetMapping("/listHis")
	public ResponseBean<List<ExamsVo>> listHis(Page page,HttpServletRequest request,
											@ApiParam(value = "试卷类型") @RequestParam(name = "extype",required = false) Integer extype,
											@ApiParam(value = "试卷名称") @RequestParam(name = "name",required = false) String name) {

//		查看自己的考试
		String token = this.getToekn(request);

		LoginUser uer = systemCommonApi.getCacheLoginUser(token);

		QueryWrapper<Exams> queryWrapper = new QueryWrapper<Exams>();
		queryWrapper.eq("student.id",uer.getId());
		if(StrUtil.isNotBlank(name)){
			queryWrapper.like("exams.name",name);
		}
		if(extype!=null){
			queryWrapper.eq("exampaper.type",extype);
		}
		queryWrapper.apply("exams.end_time <= now()");
		queryWrapper.like("exams.sid",'"'+uer.getName()+'"');
//		降序
		queryWrapper.orderByDesc("crtm");
		Page<ExamsVo> examsVoPage = iExamsService.selectHisExamVo(page, queryWrapper);

		Iterator<ExamsVo> iterator21 = examsVoPage.getRecords().iterator();
		while (iterator21.hasNext()){
			ExamsVo next = iterator21.next();
			next.setJige(0);
			if (next.getAnscore()!=null && next.getAnscore()!="") {
				if (Integer.parseInt(next.getAnscore()) >= next.getPassScore()) {
					next.setJige(next.getJige() + 1);
				}
			}
		}


		/////////////////////////keli/////////////////////////
		QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
		answersQueryWrapper.eq("sid",uer.getId());
		List<Answers> list = iAnswersService.list(answersQueryWrapper);
		List<ExamsVo> records = examsVoPage.getRecords();
		Iterator<ExamsVo> iterator = records.stream().iterator();
		//开始考试状态赋值
		while (iterator.hasNext()){
			ExamsVo next = iterator.next();
			if (next.getEndTime().compareTo(new Date())==-1){
				next.setState(1);
			}
			Iterator<Answers> iterator1 = list.iterator();
			while (iterator1.hasNext()){
				Answers next1 = iterator1.next();
				if (next1.getKid().equals(next.getId())&&(System.currentTimeMillis()-next1.getStartexam())>(next.getExduration()*60*1000)){
					next.setState(1);
				}
			}
		}
		//历史记录状态赋值
		Iterator<ExamsVo> iterator2 = records.stream().iterator();
		while (iterator2.hasNext()){
			ExamsVo next = iterator2.next();
			Iterator<Answers> iterator1 = list.iterator();
			while (iterator1.hasNext()){
				Answers next1 = iterator1.next();
				System.out.println(next1);
				if (next1.getKid().equals(next.getId())&&next1.getAnswer()!=null){
					next.setLookState(1);
				}
			}
		}




		return ResponseBean.Success(records);
	}

//	@ApiOperation(value = "添加")
////	@RequiresPermissions("")
//	@PostMapping("/save")
//	public ResponseBean save(@RequestBody Exams record) {
//		if (iExamsService.save(record)) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//	@ApiOperation(value = "去编辑")
////	@RequiresPermissions("")
//	@GetMapping("/toUpdate")
//	public ResponseBean<Exams> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
//		Exams exams = iExamsService.getById(id);
//		if (ObjectUtil.isNotNull(exams)) {
//			return ResponseBean.Success(exams);
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//
//	@ApiOperation(value = "编辑")
////	@RequiresPermissions("")
//	@PutMapping("/update")
//	public ResponseBean update(@RequestBody Exams record) {
//		if (iExamsService.updateById(record)) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//	@ApiOperation(value = "删除")
////	@RequiresPermissions("")
//	@DeleteMapping("/remove")
//	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
//		if (iExamsService.removeByIds(Arrays.asList(ids))) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}

////////////////////////keli////////////////////////////////////////////////////////////////
	@ApiOperation(value = "获得考试试卷字段内容")
//	@RequiresPermissions("")
	@GetMapping("/getexpContent")
	public ResponseBean getexpContent(@ApiParam(value = "expid") @RequestParam(name = "expid") String expid) {
		String expContent = iExamsService.selectContent(expid);
		return ResponseBean.Success("请求成功",expContent);
	}

	@ApiOperation(value = "获得考试题目")
//	@RequiresPermissions("")
	@GetMapping("/selectQuestionContent")
	public ResponseBean selectQuestionContent(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		String content = iExamsService.selectQuestionContent(id);
		return ResponseBean.Success("请求成功",content);
	}


}
