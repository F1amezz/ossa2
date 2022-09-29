package com.ossjk.qlh.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.system.entity.Thirdaccount;
import com.ossjk.qlh.system.mapper.ThirdaccountMapper;
import com.ossjk.qlh.system.service.IThirdaccountService;

/**
 * Copyright 2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: ThirdaccountServiceImpl
 * @Description: Thirdaccount-服务实现类
 * @author: chair
 * @date: 2021-08-09 10:45:44
 */
@Service
public class ThirdaccountServiceImpl extends ServiceImpl<ThirdaccountMapper, Thirdaccount> implements IThirdaccountService {

	@Override
	public boolean saveWechatBind(String uid, String openid) {
		//删掉该openid的与其他帐号的绑定记录	
		//类型 1-微信、2-QQ、3-阿里
		this.remove(new UpdateWrapper<Thirdaccount>().eq("type", "1").eq("openid", openid));
		return this.save(new Thirdaccount(null,uid,openid,1));
	}

}
