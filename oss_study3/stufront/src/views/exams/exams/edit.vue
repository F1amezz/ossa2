<template>
  <popup ref="popup" :loading="popupLoading" @beforeClose="close">
    <div slot="body">
      <el-row>
        <!-- 单选 -->
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[0]" :key="index + '321'">
          {{ i.question.title }} <b> 答案：{{ i.answer[0] }}</b>
          <el-card v-for="(j, index) in i.question.choiceList" :key="index + 'choiceLit'"><b>{{ j.o }}.</b>{{ j.n }}
          </el-card>
        </el-card>
        <!-- 多选 -->
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[1]" :key="index + '71'">{{ i.question.title
        }}<b>
            答案：
            <span v-for="(y, index) in i.answer" :key="index + 'k1'">{{
                y
            }}</span>
          </b>
          <el-card v-for="(j, index) in i.question.choiceList" :key="index + 'choiceL4ist'"><b>{{ j.o }}.</b>{{ j.n }}
          </el-card>
        </el-card>

        <!-- 判断 -->
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[2]" :key="index + '11'">{{ i.question.title }}
          <b>答案：{{ i.answer == 1 ? "正确" : ""
          }}{{ i.answer == 2 ? "错误" : "" }}</b>
        </el-card>

        <!-- 填空 -->
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[3]" :key="index + '12'">{{ i.question.title }}
          <b>答案：
            <span v-for="(j, index) in i.answer" :key="index + 'chaddeL4ist'">{{ j }}
            </span>
          </b>
        </el-card>

        <!-- 论述题 -->
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[4]" :key="index + '13'">{{ i.question.title }}
          <br> <b>答案：
            {{ i.answer[0] }}</b>

        </el-card>
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[5]" :key="index + '14'">{{ i.question.title
        }}<br> <b>答案：
            {{ i.answer[0] }}</b></el-card>
        <el-card class="box-card" v-for="(i, index) in this.orderanswers[6]" :key="index + '51'">{{ i.question.title
        }}<br> <b>答案：
            {{ i.answer[0] }}</b></el-card>
      </el-row>
    </div>
    <div slot="footer">
      <el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">关 闭</el-button>

    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/exams/exams/exams.js";
import { log } from "console";
export default {
  name: "edit",
  components: {
    popup,
  },
  data() {
    return {
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      answers: {},
      orderanswers: [],
      row: {},
    };
  },
  methods: {
    //打开弹框
    async open(parameter, title, disabled, size) {
      console.log("------------------");
      this.row = parameter.id;
      this.disabled = disabled;
      this.$refs.popup.open(title, size);
      this.$nextTick(() => {
        this.init();
        this.$set;
      });



    },
    //关闭弹框
    close() {
      //初始化

      this.answers = {};
      this.orderanswers = [];
      this.row = {};
    },
    confirm() {
      this.close()
      this.$refs.popup.close()


    },

    init() {
      //获取答案数据
      api.getOneAnswers(this.row, (response) => {
        this.answers = JSON.parse(response.data.answer);
        console.log(response, "this.answers-");
        //答案对象重排序
        for (let index = 0; index < 7; index++) {
          // this.orderanswers[0]=
          switch (this.answers.types[index]) {
            case 1:
              this.$set(this.orderanswers, 0, this.answers.contents[index].qid);
              break;
            case 2:
              this.$set(this.orderanswers, 1, this.answers.contents[index].qid);

              break;
            case 3:
              this.$set(this.orderanswers, 2, this.answers.contents[index].qid);

              break;
            case 4:
              this.$set(this.orderanswers, 3, this.answers.contents[index].qid);

              break;
            case 5:
              this.$set(this.orderanswers, 4, this.answers.contents[index].qid);

              break;
            case 6:
              this.$set(this.orderanswers, 5, this.answers.contents[index].qid);

              break;
            case 7:
              this.$set(this.orderanswers, 6, this.answers.contents[index].qid);

              break;

            default:
              break;
          }
        }
        console.log(this.orderanswers, "orderanswers");
      });
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
