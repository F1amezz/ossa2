package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.entity.Subject;
import com.ossjk.qlh.edu.service.ISubjectService;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.service.IQuestionService;
import com.ossjk.qlh.exams.vo.QuestionSubVo;
import com.ossjk.qlh.stus.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: QuestionController
 * @Description: Question-控制器
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Api(tags = "试题表")
@RestController
@RequestMapping("/exams/question")
public class QuestionController extends BaseController {
	
	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private ResourceMappersProperties ymlUri;
	@ApiOperation(value = "列表")
	@RequiresPermissions("00160000")
	@GetMapping("/list")
	public ResponseBean<Page<QuestionSubVo>> list(Page page,@ApiParam(value = "试题类型") @RequestParam(name = "type",required = false) Integer type,
												  @ApiParam(value = "题目") @RequestParam(name = "name",required = false) String name,
												  @ApiParam(value = "试题来源") @RequestParam(name = "reference",required = false) String reference) {
		QueryWrapper<Question> queryWrapper = new QueryWrapper<Question>();

		if(type!=null){
			queryWrapper.eq("question.type",type);
		}
		if(StrUtil.isNotBlank(name)){
			queryWrapper.like("question.name","%"+name+"%");
		}
		if(StrUtil.isNotBlank(reference)){
			queryWrapper.eq("question.reference",reference);
		}
		//根据创建时间降序排序
		queryWrapper.orderByDesc("crtm");

		Page<QuestionSubVo> questionSubVoPage = iQuestionService.selectQuSubVo(page, queryWrapper);
		return ResponseBean.Success(questionSubVoPage);
	}

	@ApiOperation(value = "添加")
	@RequiresPermissions("001600000005")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Question record) {
		if (iQuestionService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	@RequiresPermissions("001600000010")
	@GetMapping("/toUpdate")
	public ResponseBean<Question> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Question question = iQuestionService.getById(id);
		if (ObjectUtil.isNotNull(question)) {
			System.out.println("question === " + question);
			return ResponseBean.Success(question);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	@RequiresPermissions("001600000010")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Question record) {
		if (iQuestionService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("001600000015")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iQuestionService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
	@ApiOperation(value = "去确认导入")
	@RequiresPermissions("001600000005")
	@GetMapping("/toComfirm")
	public ResponseBean<List<Question>> toComfirm(@ApiParam(value = "fname") @RequestParam(name = "fname") String fname) {
		List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();
		Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));
		List<Question> eqs = iQuestionService.parseQuestion(new File(uriMap.get("/statics/exam") ,fname));
		System.out.println(eqs);
		if(!eqs.isEmpty()&&eqs.size()>0){
			return ResponseBean.Success(eqs);
		}else{
			return ResponseBean.Fail();
		}

	}
	@ApiOperation(value = "完成导入")
	//@RequiresPermissions("")
	@PutMapping("/finishImport")
	public ResponseBean importDatas( @RequestBody List<Question> records  ) {
		for (Question question :records  ) {
			String sname = iSubjectService.parseSidBySname(question.getSbid());
			question.setSbid(sname);
				if(question.getType()<5){
					question.setIsSubjective(2);
				}else{
					question.setIsSubjective(1);
				}

		}
		Boolean flag = iQuestionService.saveBatch(records);
		if (flag) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}

	}
}
