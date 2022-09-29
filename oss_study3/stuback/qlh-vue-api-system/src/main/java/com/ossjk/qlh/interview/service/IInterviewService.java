package com.ossjk.qlh.interview.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.interview.entity.Company;
import com.ossjk.qlh.interview.entity.Interview;
import com.ossjk.qlh.interview.entity.Posts;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.service
 * @ClassName: MianshiService
 * @Description:  面试表-服务类接口
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
public interface IInterviewService extends IService<Interview> {

    boolean saveDTO(Interview intv,  Company cmpy,   Posts post);


}
