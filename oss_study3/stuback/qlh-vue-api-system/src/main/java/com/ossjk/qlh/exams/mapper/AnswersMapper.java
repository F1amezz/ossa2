package com.ossjk.qlh.exams.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.exams.entity.Answers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 * 答卷（题）表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-08-01
 */
public interface AnswersMapper extends BaseMapper<Answers> {

    @Select("select id from answers where sid =#{sid}  and " +
            " #{nowTm} between  crtm and   date_add(crtm, interval " +
            " (select duration from exampaper where  id=#{paperId})  minute)")
    String selectExamInTime(@Param("sid") String sid, @Param("nowTm") Date nowTm, @Param("paperId") String paperId);

    @Select("select  expid  from answers a , " +
            "    (select sid,max(crtm) as crtm  from answers where sid =#{sid} and answer is null) b" +
            "  where a.sid=b.sid and a.crtm = b.crtm")
    String selectMaxNullExpID(@Param("sid") String sid );

}
