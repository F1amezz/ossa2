package com.ossjk.qlh.interview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.interview.entity.Company;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.mapper
 * @ClassName: CompanyMapper
 * @Description: 企业表-Mapper
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
public interface CompanyMapper extends BaseMapper<Company> {

    @Select("select * from Company where name like #{name}")
    List<Company> interaCname(@Param("name") String name);


}
