package com.ossjk.qlh.interview.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.vo.PostsVO;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.mapper
 * @ClassName: PostsMapper
 * @Description: 岗位表-Mapper
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
public interface PostsMapper extends BaseMapper<Posts> {

    @Select("select a.*,b.offered from  (select p.*,c.name as cname,    count(m.id) as mtimes from posts p left join  company c " +
            "   on p.company = c.id  left join interview m on m.postid = p.id   ${ew.customSqlSegment}  group by p.id,c.name)  a left join " +
            "(select p.id,   count(m.id) as offered from posts p left join interview m on m.postid = p.id " +
            " where  msresult='offer'  group by p.id) b  on a.id = b.id ")
    Page<PostsVO>  selectVO(Page<PostsVO> page, QueryWrapper<Posts> ew);

}
