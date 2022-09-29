package com.ossjk.qlh.sms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.sms.entity.Sms;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Copyright  2021-04-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.sms.mapper
 * @ClassName: SmsMapper
 * @Description: 站内信息表-Mapper
 * @author: chair
 * @date:  2021-04-05 22:44:33 
 */
public interface SmsMapper extends BaseMapper<Sms> {

    Page selectPageVo(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Sms getVoById(@PathVariable("id") String id);
}
