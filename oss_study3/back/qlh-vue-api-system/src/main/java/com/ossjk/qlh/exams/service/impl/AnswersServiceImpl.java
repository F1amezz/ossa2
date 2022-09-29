package com.ossjk.qlh.exams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.exams.entity.Answers;
import com.ossjk.qlh.exams.mapper.AnswersMapper;
import com.ossjk.qlh.exams.service.IAnswersService;
import org.springframework.stereotype.Service;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service.impl
 * @ClassName: AnswersServiceImpl
 * @Description: 答卷（题）表-服务实现类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Service
public class AnswersServiceImpl extends ServiceImpl<AnswersMapper, Answers> implements IAnswersService {

}
