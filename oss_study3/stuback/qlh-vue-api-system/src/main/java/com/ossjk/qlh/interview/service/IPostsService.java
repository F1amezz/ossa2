package com.ossjk.qlh.interview.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.vo.PostsVO;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.service
 * @ClassName: PostsService
 * @Description:  岗位表-服务类接口
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
public interface IPostsService extends IService<Posts> {

    Page<PostsVO> selectVO(Page<PostsVO> page, QueryWrapper<Posts> ew);
}
