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
              <el-autocomplete
                v-model="formParameter.sname"
                class="inline-input"
                :fetch-suggestions="queryCourse"
                placeholder="请输入学生名字"
                :trigger-on-focus="false"
                @select="handleSelect"
                @change="getStudent"
              ></el-autocomplete>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="班级" prop="cid">
              <el-input v-model="formParameter.cname" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标题" prop="title">
              <el-input v-model="formParameter.title" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="周数" prop="week">
              <el-input v-model="formParameter.week" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="问题1-周自我评价" prop="quest1">
              <select-option-dictionary v-model="formParameter.quest1" dkey="week-selfevaluation"></select-option-dictionary>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="问题2-周游戏时长" prop="quest2">
              <select-option-dictionary v-model="formParameter.quest2" dkey="week-game"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="问题3-周进度评价" prop="quest3">
              <select-option-dictionary v-model="formParameter.quest3" dkey="weekly-progress"></select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="自我评价内容" prop="content">
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
          <el-col :span="8">
            <el-form-item label="已读状态" prop="readst" v-if="disabled">
              <select-option-dictionary
                v-model="formParameter.readst"
                dkey="readst"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="学生提交时间" prop="subtime" v-if="disabled">
              <el-date-picker
                v-model="formParameter.subtime"
                type="datetime"
                :disabled="true"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="disabled">
            <el-form-item label="老师审阅时间" prop="readtime">
              <el-date-picker
                v-model="formParameter.readtime"
                type="datetime"
                placeholder="选择日期"
                :disabled="true"
              ></el-date-picker>
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
import api from "@/api/study/selfevaluation/selfevaluation.js";
import clazzApi from "@/api/edu/clazz/clazz.js";
import stuApi from "@/api/stus/student/student.js";
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
      // show:flase,
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
        ]
      }
    };
  },
  methods: {
    queryCourse(queryString, cb) {
      stuApi.list({ name: queryString }, (res) => {
        let stus = res.data.records;
        let results = queryString
          ? stus.filter(this.studentsFilter(queryString))
          : stus;
        // 调用 callback 返回建议列表的数据
        cb(results);
      });
    },
    studentsFilter(queryString) {
      return (stu) => {
        stu.value = stu.name;
        return stu.value.indexOf(queryString) === 0;
      };
    },
    handleSelect(item) {
      this.formParameter.sid = item.id;
      this.getStudent();
    },
    getStudent() {
      clazzApi.student2({ sid: this.formParameter.sid }, (response) => {
        this.$set(this.formParameter, "cname", response.data.clazz.name);
        this.$set(
          this.formParameter,
          "course",
          response.data.clazz.dbColumn_cid
        );
        this.$set(
          this.formParameter,
          "tname",
          response.data.clazz.dbColumn_tid
        );
      });
    },
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
          this.formParameter.subtime = new Date().format("yyyy-MM-dd hh:mm:ss");
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
          this.formParameter.subtime = new Date().format("yyyy-MM-dd hh:mm:ss");
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
        console.log(parameter);
        api.toUpdate(parameter, response => {
          this.formParameter = response.data.records[0];
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
