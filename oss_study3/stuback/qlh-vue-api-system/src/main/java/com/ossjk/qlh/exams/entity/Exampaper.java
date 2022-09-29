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
 * 试卷
 * </p>
 *
 * @author admin
 * @since 2022-08-01
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Exampaper  extends BaseEntity<Exampaper> {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
      private String id;

      /**
     * 试卷名称
     */
      @TableField("name")
      private String name;

      /**
     * 考试时间（分钟）
     */
      private Integer duration;

      /**
     * 总分
     */
      private Integer score;

      /**
     * 及格分数
     */
      private Integer passScore;

      /**
     * 类型：1-小测、2-阶段考、3-毕业考
     */
      private Integer type;

      /**
     * 是否可见 1-可见、2-不可见
     */
      private Integer isVisible;

      /**
     * 试题例子{types:[1,2,3],scores:[20,40,40],qnums:[5,10,2]contexts:[{qid:[1,2,3,4,5]qscore[4,4,4,4,4]},{qid:[1,2,3,4,5,6,7,8,9,10]qscore[4,4,4,4,4,4,4,4,4,4]},{qid:[11,12]qscore[10,30]}]}
     */
      private String content;

      /**
     * 状态 1-启用、2-不启用
     */
      private Integer status;

      /**
     * 备注
     */
      private String mark;

      /**
     * 创建时间
     */
      private Date crtm;

      /**
     * 修改时间
     */
      private Date mdtm;

      /**
     * 修改人
     */
      private String mder;

      /**
     * 创建人
     */
      private String crer;

      /**
     * 课程id
     */
      private String cid;

      /**
     * 模块id
     */
      private String mid;

      /**
     * 科目id
     */
      private String kid;


}
