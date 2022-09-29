package com.ossjk.qlh.sms.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.sms.entity.Templates;
import com.ossjk.qlh.sms.mapper.TemplatesMapper;
import com.ossjk.qlh.sms.service.ITemplatesService;

/** 
 * Copyright  2021-04-27 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.service.impl
 * @ClassName: TemplatesServiceImpl
 * @Description: 信息模版-服务实现类
 * @author: chair
 * @date:  2021-04-27 10:23:22 
 */
@Service
public class TemplatesServiceImpl extends ServiceImpl<TemplatesMapper, Templates> implements ITemplatesService {

}
