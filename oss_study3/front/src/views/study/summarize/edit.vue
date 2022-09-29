<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="学生" prop="sid">
              <el-input v-model="formParameter.dbColumn_sid" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级" prop="cid">
              <el-input v-model="formParameter.dbColumn_cid" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="老师" prop="tid">
              <el-input v-model="formParameter.dbColumn_tid" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程" prop="courseid">
              <el-input v-model="formParameter.dbColumn_courseid" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="问题1-讲授语速" prop="a1">
              <select-option-dictionary v-model="formParameter.a1" dkey="feedback-quest1"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题2-课堂案例" prop="a2">
              <select-option-dictionary v-model="formParameter.a2" dkey="feedback-quest2"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题3-答疑解忧" prop="a3">
              <select-option-dictionary v-model="formParameter.a3" dkey="feedback-quest3"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题4-听课情况" prop="a4">
              <select-option-dictionary v-model="formParameter.a4" dkey="feedback-quest4"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题5-练习完成" prop="a5">
              <select-option-dictionary v-model="formParameter.a5" dkey="feedback-quest5"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题6-讲师技能" prop="a6">
              <select-option-dictionary v-model="formParameter.a6" dkey="feedback-quest6"></select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <el-input
                type="textarea"
                placeholder="请输入..."
                :autosize="{ minRows: 6, maxRows: 8 }"
                v-model="formParameter.content"
                autocomplete="off"
                maxlength="300"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="学生提交时间" prop="subtime">
              <el-date-picker v-model="formParameter.subtime" type="datetime" placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="已读状态" prop="readst">
              <select-option-dictionary v-model="formParameter.readst" dkey="readst"></select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="老师查看时间">
              <el-date-picker v-model="formParameter.chetime" type="datetime" placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/study/summarize/summarize.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary
  },
  data() {
    return {
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      rules: {
        //学生名字
        sname: [
          {
            validator: this.$validate.required,
            trigger: "blur"
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur"
          }
        ],
        // //班级名字
        // cname: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // subtime: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //班主任
        // tname: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //课程+章节
        // course: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //内容
        // content: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],

        // //反馈1
        // a1: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //反馈2
        // a2: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //反馈3
        // a3: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //反馈4
        // a4: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //反馈5
        // a5: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ],
        // //反馈6
        // a6: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur"
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur"
        //   }
        // ]
      }
    };
  },
  methods: {
    //打开弹框
    open(parameter, title, disabled, size) {
      this.parameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, size);
      this.$nextTick(() => {
        this.init();
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },
    //提交表单
    confirm() {
      this.$utils.checkForm(this.$refs.form, () => {
        this.confirmLoading = true;
        if (!this.parameter.id) {
          //添加
          let parameter = this.$utils.merger(this.formParameter);
          api.save(
            parameter,
            response => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            response => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        } else {
          //编辑
          let parameter = this.$utils.merger(this.formParameter);
          api.update(
            parameter,
            response => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            response => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        }
      });
    },
    init() {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;
      //清空内容
      this.formParameter = {};
      //清楚校验
      this.$refs.form.clearValidate();

      if (this.parameter.id) {
        //编辑查询数据
        let parameter = {
          id: this.parameter.id
        };
        api.toUpdate(parameter, response => {
          this.formParameter = response.data;
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    }
  }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
