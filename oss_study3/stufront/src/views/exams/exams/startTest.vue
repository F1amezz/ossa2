<template>
  <popup ref="popup" :loading="popupLoading" @beforeClose="close">
    <div slot="body" style="padding-top: 0">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
      >
        <el-row>
          <el-col :span="24">
            <!-- 单选 -->
            <el-card v-for="(i, index1) in questionArray.t1" :key="index1">
              <b>{{ i.i + 1 }}.{{ index1 + 1 }}</b> <b>{{ i.question.title }}</b>
              <b>(</b> <b>{{ i.flag }}</b
              ><b>)</b>
              <div
                v-for="(i, index) in i.question.choiceList"
                :key="index + '1'"
              >
                <el-button
                  :class="i.n"
                  plain
                  @click="
                    singleChoice(questionArray.t1[index1], i.o, index1, i.n)
                  "
                  > <b> {{ i.o }}.</b>{{ i.n }}
                </el-button>
              </div>
            </el-card>

            <!-- 多选 -->
            <el-card
              v-for="(i, index1) in questionArray.t2"
              :key="index1 + '2'"
            >
              <b>{{ i.i + 1 }}.{{ index1 + 1 }}</b> <b>{{ i.question.title }}</b>
              <b>(</b> <b v-for="(j, ind) in i.flag" :key="ind + '5'">{{ j }}</b
              ><b>)</b>
              <div
                v-for="(i, index) in i.question.choiceList"
                :key="index + '1'"
              >
                <el-button
                  plain
                  :class="i.n + index1"
                  @click="
                    moreChoice(
                      questionArray.t2[index1],
                      i.o,
                      index1,
                      index,
                      i.n + index1
                    )
                  "
                  > <b> {{ i.o }}.</b>{{ i.n }}</el-button
                >
              </div>
            </el-card>
            <!-- 判断 -->
            <el-card v-for="(i, index) in questionArray.t3" :key="index + '3'">
              <b>{{ i.i + 1 }}.{{ index + 1 }}</b> <b>{{ i.question.title }}</b> <br />

              <input
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer[0]
                "
                value="1"
                type="radio"
                :name="`${index}`"
              />正确
              <input
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer[0]
                "
                value="2"
                type="radio"
                :name="`${index}`"
              />错误
            </el-card>
            <!-- 填空 -->
            <el-card v-for="(i, index) in questionArray.t4" :key="index + '4'">
              <!-- <b>{{ i.i + 1 }}.{{ index + 1 }}</b> {{ i.question.title }}
              <br /><br />
              <input
                type="text"
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer.value
                "
              /> -->
              <FillInTheBlanks
                :questionNumber="i.i + 1 + '.' + (index + 1)"
                :title="i.question.title"
                @answer="getAnswerArr($event, i)"
              ></FillInTheBlanks>
            </el-card>
            <el-card v-for="(i, index) in questionArray.t5" :key="index + '5'">
              <b>{{ i.i + 1 }}.{{ index + 1 }}</b> <b>{{ i.question.title }}</b>  <br />
              <textarea
                name=""
                id=""
                cols="100%"
                rows="5"
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer[0]
                "
              ></textarea>
            </el-card>
            <el-card v-for="(i, index) in questionArray.t6" :key="index + '6'">
              <b>{{ i.i + 1 }}.{{ index + 1 }}</b> <b>{{ i.question.title }}</b> <br />
              <textarea
                name=""
                id=""
                cols="100%"
                rows="5"
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer[0]
                "
              ></textarea>
            </el-card>
            <el-card v-for="(i, index) in questionArray.t7" :key="index + '7'">
              <b>{{ i.i + 1 }}.{{ index + 1 }}</b> <b>{{ i.question.title }}</b> <br />
              <textarea
                name=""
                id=""
                cols="100%"
                rows="5"
                v-model="
                  studentAnswer.answer.contents[i.i].qid[i.j].answer[0]
                "
              ></textarea>
            </el-card>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-row>
        <el-col :span="21">
          <el-button
            v-if="!disabled"
            type="primary"
            :loading="confirmLoading"
            @click="confirm()"
            >交 卷</el-button
          >
        </el-col>
        <el-col :span="3">
          {{ countDown }}
        </el-col>
      </el-row>

      <!-- <el-button @click="close">取 消</el-button> -->
    </div>
  </popup>
</template>
<script>
import popup from "@/components/popup/drawerPopupSta.vue";
import FillInTheBlanks from "@/components/exams/FillInTheBlanks.vue";
import api from "@/api/exams/exams/exams.js";
export default {
  components: {
    popup,
    FillInTheBlanks,
  },
  data() {
    return {
      countDown: "",
      popupLoading: false,
      disabled: false,
      confirmLoading: false,
      parameter: {},
      flag1: 0,
      formParameter: {},
      //学生答案对象
      studentAnswer: {},
      //题类型位置
      choice_question: { show: false },
      //题数组
      questionArray: { t1: [], t2: [], t3: [], t4: [], t5: [], t6: [], t7: [] },
      dateTimeSetInterval: function () {},
    };
  },

  methods: {
    //毫秒转时间
    formatDuring(mss) {
      var days = parseInt(mss / (1000 * 60 * 60 * 24));
      var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
      var seconds = (mss % (1000 * 60)) / 1000;
      return (
        hours + " 小时 " + minutes + " 分钟 " + Math.round(seconds) + " 秒 "
      );
    },

    //初始化学生答案对象
    initStudentAnswer(row, timeDown) {
      //倒计时定时器

      var dateTime = timeDown;

      this.dateTimeSetInterval = setInterval(() => {
        dateTime = dateTime - 1000;
        if (dateTime < 0) {
          this.strongclose();
        }
        this.countDown = this.formatDuring(dateTime);
      }, 1000);

      this.flag1 = 1;
      console.log(row);
      //基础属性赋值
      this.studentAnswer.kid = row.id;
      this.studentAnswer.cid = row.cid;
      this.studentAnswer.sid = row.stuid;
      //this.studentAnswer.score="";
      this.studentAnswer.expid = row.expid;
      api.getexpContent({ expid: row.expid }, (response) => {
        this.studentAnswer.answer = JSON.parse(response.data);

        //根据题目id赋值题目
        for (let i = 0; i < this.studentAnswer.answer.contents.length; i++) {
          this.studentAnswer.answer.contents[i].qid;
          for (
            let j = 0;
            j < this.studentAnswer.answer.contents[i].qid.length;
            j++
          ) {
            let flag = this.studentAnswer.answer.contents[i].qid[j];

            JSON.parse(
              JSON.stringify(
                (this.studentAnswer.answer.contents[i].qid[j] = {
                  id: flag,
                  question: "",
                  answer: [],
                })
              )
            );
            //待优化成一个数组一次查询
            api.selectQuestionContent({ id: flag }, (response) => {
              this.studentAnswer.answer.contents[i].qid[j].question =
                JSON.parse(response.data);

              if (this.studentAnswer.answer.types[i] == 1) {
                this.questionArray.t1.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                  flag: "",
                });
              } else if (this.studentAnswer.answer.types[i] == 2) {
                this.questionArray.t2.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                  flag: ["", "", "", "", "", ""],
                });
              } else if (this.studentAnswer.answer.types[i] == 3) {
                this.questionArray.t3.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                });
              } else if (this.studentAnswer.answer.types[i] == 4) {
                this.questionArray.t4.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                });
              } else if (this.studentAnswer.answer.types[i] == 5) {
                this.questionArray.t5.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                });
              } else if (this.studentAnswer.answer.types[i] == 6) {
                this.questionArray.t6.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                });
              } else if (this.studentAnswer.answer.types[i] == 7) {
                this.questionArray.t7.push({
                  question: JSON.parse(response.data),
                  i: i,
                  j: j,
                });
              }
            });
          }
        }
      });
    },
    //单选赋值
    singleChoice(flag, choice, index1, $eClass) {
      // 设置点击时样式
      // console.log(this.questionArray, "---------this.questionArray--------");
      // 获取当前按钮
      const currentClass = document.getElementsByClassName($eClass)[0];
      // 当前按钮的父亲DIV的兄弟们
      const btnParentBrother = currentClass.parentNode.parentNode.children;
      // console.log(btnParentBrother, '---------btnParentBrother-------');
      // 循环消除其他按钮颜色
      for (
        let i = btnParentBrother.length - 1;
        i > btnParentBrother.length - 5;
        i--
      ) {
        btnParentBrother[i].firstChild.style.backgroundColor = "white";
      }
      // 给当前按钮加背景色
      currentClass.style.backgroundColor = "skyblue";

      console.log(this.radio);
      this.studentAnswer.answer.contents[flag.i].qid[flag.j].answer[0] = choice;
      this.questionArray.t1[index1].flag = choice;
      console.log(this.questionArray);
    },
    //获取填空题的答案数组,并且绑定到studentAnswer.answer上
    getAnswerArr(answerArr, i) {
      this.studentAnswer.answer.contents[i.i].qid[i.j].answer = answerArr;
      // console.log('-------------');
      // console.log(this.studentAnswer.answer);
      // console.log(i);
    },

    //判断题

    //duo选赋值
    moreChoice(flag, choice, index1, index, $eClass) {
      if (this.questionArray.t2[index1].flag[index] == choice) {
        //this.questionArray.t2[index1].flag[index] = "";
        this.$set(this.questionArray.t2[index1].flag, index, "");
      } else {
        //   this.questionArray.t2[index1].flag[index] = choice;
        this.$set(this.questionArray.t2[index1].flag, index, choice);
      }

      this.studentAnswer.answer.contents[flag.i].qid[flag.j].answer[index] =
        this.questionArray.t2[index1].flag[index];
      

      // 获取当前按钮
      const currentClass = document.getElementsByClassName($eClass)[0];
      // 给当前按钮加背景色
      if (currentClass.style.backgroundColor == "skyblue") {
        currentClass.style.backgroundColor = "white";
      } else {
        currentClass.style.backgroundColor = "skyblue";
      }
    },
    //打开弹框
    open(parameter, title, disabled) {
      this.parameter = parameter;
      this.disabled = disabled;

      this.$refs.popup.open(title, "100%");

      console.log("------------", parameter);
      // this.$nextTick(() => {
      if (this.flag1 == 0) {
        this.initStudentAnswer(parameter.row, parameter.timeDown);
      }
      // })
      console.log(this.studentAnswer, "111111111");
    },

    strongclose() {
      clearInterval(this.dateTimeSetInterval);
      let parameter = {
        kid: this.studentAnswer.kid,
        expid: this.studentAnswer.expid,
        cid: this.studentAnswer.cid,
        sid: this.studentAnswer.sid,
        answer: JSON.stringify(this.studentAnswer.answer),
      };
      api.updateAnswers(parameter, (response) => {});

      this.disabled = false;
      this.$refs.popup.closeCallback();
      //退出初始化参数

      this.countDown = "";
      this.popupLoading = false;
      this.disabled = false;
      this.confirmLoading = false;
      this.parameter = {};
      this.flag1 = 0;
      this.formParameter = {};
      this.studentAnswer = {};
      this.choice_question = { show: false };
      this.questionArray = {
        t1: [],
        t2: [],
        t3: [],
        t4: [],
        t5: [],
        t6: [],
        t7: [],
      };
    },

    close() {
      this.$confirm("您确定要结束考试并提交试卷么?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let parameter = {
            kid: this.studentAnswer.kid,
            expid: this.studentAnswer.expid,
            cid: this.studentAnswer.cid,
            sid: this.studentAnswer.sid,
            answer: JSON.stringify(this.studentAnswer.answer),
          };
          api.updateAnswers(parameter, (response) => {});

          this.disabled = false;
          this.$refs.popup.closeCallback();

          this.$emit("flush");
          //退出初始化参数
          clearInterval(this.dateTimeSetInterval);
          this.countDown = "";
          this.popupLoading = false;
          this.disabled = false;
          this.confirmLoading = false;
          this.parameter = {};
          this.flag1 = 0;
          this.formParameter = {};
          this.studentAnswer = {};
          this.choice_question = { show: false };
          this.questionArray = {
            t1: [],
            t2: [],
            t3: [],
            t4: [],
            t5: [],
            t6: [],
            t7: [],
          };
        })
        .catch(() => {});
    },
    confirm() {
      this.close();
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>