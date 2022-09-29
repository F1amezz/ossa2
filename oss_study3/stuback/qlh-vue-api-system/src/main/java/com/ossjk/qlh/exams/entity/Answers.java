package com.ossjk.qlh.exams.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 答卷（题）表
 * </p>
 * @author admin
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Answers  extends BaseEntity<Answers> {

    private static final long serialVersionUID=1L;

    private String id;

      /**
     * 考试id
     */
      @TableField("kid")
      private String kid;

      /**
     * 班级id
     */
      @TableField("cid")
      private String cid;

      /**
     * 考试人id
     */
      @TableField("sid")
      private String sid;

      /**
     * 得分
     */
      @TableField("score")
      private Integer score;

      /**
     * 考生的答案
     */
      @TableField("answer")
      private String answer;

      /**
     * 试卷id
     */
      @TableField("expid")
      private String expid;




      /**
     * 答卷是否已批
     */
      @TableField("isjudged")
      private Integer isjudged;

      @TableField("startexam")
      private Long startexam;


}
