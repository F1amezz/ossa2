package com.ossjk.qlh.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.mapper.ClazzMapper;
import com.ossjk.qlh.edu.service.IClazzService;
import com.ossjk.qlh.edu.vo.ClazzVO;
import org.springframework.stereotype.Service;

/**
 * Copyright  2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.service.impl
 * @ClassName: ClazzServiceImpl
 * @Description: 班级表-服务实现类
 * @author: Rick.yang
 * @date:  2022-06-08 19:34:03 
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {

    public Page<ClazzVO> pageVO(Page<ClazzVO> page, QueryWrapper<Clazz> ew){
        return super.baseMapper.pageVO(page,ew);
    }
    public Page<ClazzVO> pageVOByTchrID(Page<ClazzVO> page,String tchrId, QueryWrapper<Clazz> ew){

        return  null;
    }
}
