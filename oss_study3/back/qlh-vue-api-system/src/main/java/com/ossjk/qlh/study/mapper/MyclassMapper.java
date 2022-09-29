package com.ossjk.qlh.study.mapper;

import com.ossjk.qlh.study.vo.OneSqlStatisticalChartVo;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.study.entity.Myclass;
import com.ossjk.qlh.study.vo.MyclassVo;

import java.util.List;

/**
 * Copyright 2022-06-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.mapper
 * @ClassName: MyclassMapper
 * @Description: -Mapper
 * @author: Rick.yang
 * @date: 2022-06-16 17:38:14
 */
public interface MyclassMapper extends BaseMapper<Myclass> {
    @Select("SELECT * from v_clazz ${ew.customSqlSegment}")
    Page<MyclassVo> selectVo(Page page, QueryWrapper<Myclass> ew);



    @Select("select d.d as date, b.cid as cid , a.a as summarizeSubmitted,b.a-a.a as summarizeNotSubmitted ," +
            " c.a as selfevaluationSubmitted,b.a-c.a as selfevaluationNotSubmitted, f.a as feedbackSubmitted ," +
            "b.a-f.a feedbackNotSubmitted, f.c as feedbackPass,f.a-f.c as feedbackNotPass from (select subdate(curdate(),6+7*#{flagDate}) d " +
            "union all select subdate(curdate(),5+7*#{flagDate}) union all select subdate(curdate(),4+7*#{flagDate}) union all select subdate(curdate(),3+7*#{flagDate}) " +
            "union all select subdate(curdate(),2+7*#{flagDate}) union all select subdate(curdate(),1+7*#{flagDate}) union all select subdate(curdate(),0+7*#{flagDate})) " +
            "d left join (select  count(id) a,cid from student where cid=#{cid}) b on 1=1 left join (select count(distinct s.sid) a," +
            "date(s.subtime) b,cid  from summarize s where s.cid =#{cid} and date(s.subtime) <=curdate() group by date(s.subtime)) a on a.b=d.d " +
            " left join (select count(distinct s.sid) a,date(s.subtime) b,cid  from selfevaluation s where s.cid =#{cid} and" +
            " date(s.subtime) <=curdate() group by date(s.subtime)) c on c.b=d.d left join (select count(distinct f.sid) a," +
            "date(f.subtime) b ,sum(case when f.score>3 then 1 else 0 end) c from feedback f where f.cid=#{cid} and date(f.subtime)" +
            " <=curdate() group by date(f.subtime)) f on f.b=d.d order by date")
    List<OneSqlStatisticalChartVo> oneSqlStatisticalChartVo(String cid ,int flagDate);
}
