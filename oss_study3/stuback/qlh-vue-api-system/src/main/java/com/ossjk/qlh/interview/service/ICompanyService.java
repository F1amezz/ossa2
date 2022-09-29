package com.ossjk.qlh.interview.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.interview.entity.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.service
 * @ClassName: CompanyService
 * @Description:  企业表-服务类接口
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
public interface ICompanyService extends IService<Company> {

    List<Company> interaCname(  String name);

}
