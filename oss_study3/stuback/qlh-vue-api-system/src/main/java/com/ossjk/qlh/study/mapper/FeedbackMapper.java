package com.ossjk.qlh.study.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.vo.FeedbackVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright  2022-07-05 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study.mapper
 * @ClassName: FeedbackMapper
 * @Description: 总结表-Mapper
 * @author: Rick.yang
 * @date:  2022-07-05 16:48:09 
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {
//    @Select("SELECT fb.*,s.name as sname,c.name as cname,u.name as tname,co.name as course from feedback fb " +
//            " left join teacher u on fb.tid=u.id  left join student s on fb.sid=s.id " +
//            " left join clazz c on fb.cid=c.id left join course co on fb.courseid=co.id ${ew.customSqlSegment}")
    @Select("SELECT * from v_feedback ${ew.customSqlSegment}")
    Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> ew);

    @Select("SELECT s.`name` sname,t.`name`tname,c.`name` cname,c2.`name` course,`feedback`.* FROM `feedback`,`course` c2,`user` t,`clazz` c,`student` s WHERE c.`cid`=c2.`id` AND c.`id`=s.`cid` AND c.`tid`=t.`id` AND `feedback`.`sid`=s.`id` ${ew.customSqlSegment}")
    List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> ew);

    @Select("SELECT s.`name` sname,t.`name`tname,c.`name` cname,c2.`name` course,`feedback`.* FROM `feedback`,"
            + "`course` c2,`user` t,`clazz` c,`student` s WHERE c.`cid`=c2.`id` AND c.`id`=s.`cid` AND c.`tid`=t.`id`  AND `feedback`.`sid`=s.`id` AND feedback.id=#{id}")
    FeedbackVo selectVoById(@Param("id") String id);

    @Select("select max(b.b) from (select  (case when  time_to_sec(timediff(now(),crtm))<(select `duration` from exampaper where `id`=expid)*60 and answer is null  then 1 else 0 end   ) b  from (select * from answers where sid =#{sid} order by crtm desc limit 0 ,5) a) b")
    int isHaveExam(@Param("sid") String sid);

//    @Insert()

}
