package com.ossjk.qlh.interview.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.interview.entity.Company;
import com.ossjk.qlh.interview.entity.Interview;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.mapper.InterviewMapper;
import com.ossjk.qlh.interview.service.ICompanyService;
import com.ossjk.qlh.interview.service.IInterviewService;
import com.ossjk.qlh.interview.service.IPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.service.impl
 * @ClassName: MianshiServiceImpl
 * @Description: 面试表-服务实现类
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, Interview> implements IInterviewService {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IPostsService postsService;

    @Override
    @Transactional
    public boolean saveDTO(Interview intv, Company cmpy, Posts post) {

        if(companyService.saveOrUpdate(cmpy)){
            if(postsService.saveOrUpdate(post)){
                this.baseMapper.insert(intv);
                return true;
            }
        }

        return false;
    }
}
