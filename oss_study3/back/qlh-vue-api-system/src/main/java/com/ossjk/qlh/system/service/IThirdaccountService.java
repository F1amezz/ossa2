package com.ossjk.qlh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.entity.Thirdaccount;

/** 
 * Copyright  2021-08-09 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: ThirdaccountService
 * @Description: Thirdaccount-服务类接口
 * @author: chair
 * @date:  2021-08-09 10:45:44 
 */
public interface IThirdaccountService extends IService<Thirdaccount> {


	boolean saveWechatBind(String id, String wxuid);


}
