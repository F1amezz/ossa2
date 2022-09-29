<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <!-- 头部步骤条 -->
      <el-row v-if="isBatch">
        <el-col :span="6">
          <el-button @click="decrease">上一位</el-button>
          <el-button @click="increase" type="primary">下一位</el-button>
        </el-col>
        <el-col :span="12">
          <el-progress :text-inside="true" :stroke-width="24" :percentage="percentage"></el-progress>
        </el-col>
      </el-row>
      <el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true" label-width="80px"
        :rules="rules">
        <el-row>
          <el-col :span="6">
            <el-form-item label="学生" prop="sid">
              <el-autocomplete v-model="formParameter.sname" class="inline-input" :fetch-suggestions="queryCourse"
                placeholder="请输入学生名字" :trigger-on-focus="false" @select="handleSelect" @change="getStudent"
                :disabled="true"></el-autocomplete>
              <!-- <el-input v-model="formParameter.dbColumn_sid" autocomplete="off"></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班级" prop="cid">
              <el-input v-model="formParameter.cname" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="老师">
              <el-input v-model="formParameter.tname" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="课程">
              <el-input v-model="formParameter.course" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="标题" prop="title">
              <el-input v-model="formParameter.title" autocomplete="off" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="评分" prop="score">
              <el-rate v-model="formParameter.score" :colors="colors">
              </el-rate>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <!-- <el-input
                v-model="formParameter.content"
                autocomplete="off"
              ></el-input> -->
              <div v-html="formParameter.content" style="background-color:beige;"></div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" v-if="disabled">
            <el-form-item label="状态(0.未读,1.已读)" prop="readst">
              <!-- <el-input size="small" v-model="formParameter.readst" autocomplete="off"></el-input> -->
              <select-option-dictionary v-model="formParameter.readst" dkey="readst">
              </select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="disabled">
            <el-form-item label="提交时间" prop="subtime">
              <el-date-picker v-model="formParameter.subtime" align="right" type="date" placeholder="选择日期"
                :picker-options="pickerOptions" disabled>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="disabled">
            <el-form-item label="查看时间" prop="chetime">
              <el-date-picker v-model="formParameter.chetime" align="center" type="date" placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
      <el-button @click="close">取 消</el-button>
      <!-- <div style="background-color: beige;font-size: larger;">
        <span >
          评分：
        </span>
        <span>
          <el-rate v-model="formParameter.score" :colors="colors">
          </el-rate>
        </span>
      </div> -->
    </div>
  </popup>
</template>

<script>
import stuApi from "@/api/stus/student/student.js";
import clazzApi from "@/api/edu/clazz/clazz.js";
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/study/feedback/feedback.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "edit2",
  components: {
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      colors: { 2: "#99A9BF", 3: '#F7BA2A', 5: "#FF9900" },//rate评分颜色
      isBatch: false,       //是否为批量处理
      percentage: 0,      //批量评分时的进度条百分比
      idArrIndex: 0,       //批量评分时的索引
      idsArr: [],          //批量评分时的数组
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      rules: {
        //学生名字

        //内容
        content: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 100,
            trigger: "blur",
          },
        ],
        //评分
        score: [
          //   {
          //     validator: this.$validate.required,
          //     trigger: "blur",
          //   },
          //   {
          //     validator: this.$validate.length,
          //     max: 10,
          //     trigger: "blur",
          //   },
        ],
        //已读状态（0-未读，1-已读）
        readst: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 10,
            trigger: "blur",
          },
        ],
        //学生提交时间
        subtime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //老师查看时间
        chetime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //标题
        title: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
      },
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
        // console.log(response.data.clazz);
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
        this.$set(this.formParameter, "cname", response.data.clazz.name);
      });
    },
    // 批量评分的“上一位”学生
    decrease() {
      if (this.idArrIndex > 0) {
        this.idArrIndex--;
        // this.percentage = ((this.idArrIndex + 1) / this.idsArr.length).toFixed(2) * 100;
      }
    },
    // 批量评分的“下一位”学生
    increase() {
      if (this.idArrIndex + 1 < this.idsArr.length) {
        this.idArrIndex++;
        // this.percentage = ((this.idArrIndex + 1) / this.idsArr.length).toFixed(2) * 100;
        this.confirm(true);
      }
    },
    //打开弹框
    open(parameter, title, disabled, size) {
      // 批量执行的（批量评分）
      if (title === "批量评分") {
        this.idsArr = parameter;
        this.isBatch = true;
        this.parameter = parameter[0];
        this.percentage = parseInt((this.idArrIndex + 1) / this.idsArr.length * 100);
      } else {
        // 单独执行的（评分）
        this.parameter = parameter;
      }
      this.disabled = disabled;
      this.$refs.popup.open(title, "70%");
      this.$nextTick(() => {
        this.init();
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      // 重置一下批量评分的操作
      this.isBatch = false;
      this.idArrIndex = 0;
      this.percentage = 0;

      this.$refs.popup.close();
    },
    //提交表单
    confirm(isNext) {
      this.$utils.checkForm(this.$refs.form, () => {
        this.confirmLoading = true;
        if (!this.parameter.id) {
          //添加
          console.log(this.formParameter.sid);
          this.formParameter.subtime = new Date().format("yyyy-MM-dd");
          let parameter = this.$utils.merger(this.formParameter);
          api.save(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        } else {
          //评分
          this.formParameter.chetime = new Date().format("yyyy-MM-dd");
          let parameter = this.$utils.merger(this.formParameter);
          api.update(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              if (!isNext) {
                this.close();
              }
              this.$parent.list();
            },
            (response) => {
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
        //打开“抽屉”弹窗时去后端查询数据
        let parameter = {
          id: this.parameter.id,
        };
        api.toUpdate(parameter, (response) => {
          console.log('当前评分对象',response.data);
          this.formParameter = response.data;
          this.formParameter.score = response.data.score * 1;
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    },
  },
  watch: {
    idArrIndex: {
      handler(newValue, oldValue) {
        this.percentage = parseInt((newValue + 1) / this.idsArr.length * 100);
        this.parameter = this.idsArr[newValue];
        this.init();
      },
    }
  }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";

.dataForm {
  margin-top: 20px;
}
</style>
