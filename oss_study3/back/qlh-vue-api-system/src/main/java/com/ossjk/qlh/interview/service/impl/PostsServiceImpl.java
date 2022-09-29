package com.ossjk.qlh.interview.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.mapper.PostsMapper;
import com.ossjk.qlh.interview.service.IPostsService;
import com.ossjk.qlh.interview.vo.PostsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.service.impl
 * @ClassName: PostsServiceImpl
 * @Description: 岗位表-服务实现类
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {


    @Override
    @Transactional
    public boolean save(Posts entity) {

      if( ObjectUtil.isNull(entity.getBgntm())    ){
          //默认当前
          entity.setBgntm(new Date());
          Calendar c =   Calendar.getInstance();
          //默认一个月
          c.getInstance().add( Calendar.DAY_OF_MONTH,30  );
          entity.setEndtm(c.getTime());
      }

        return super.save(entity);
    }

    public  Page<PostsVO> selectVO(Page<PostsVO> page, QueryWrapper<Posts> ew){
        return this.baseMapper.selectVO(page,ew);
    }
}
