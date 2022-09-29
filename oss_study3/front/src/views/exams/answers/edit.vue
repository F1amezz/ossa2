<template>
  <popup ref="popup" :loading="popupLoading" append-to-body>
    <div slot="body">
      <el-row>
        <el-col :span="24">
          <el-card
            v-for="(i, index) in questions[0]"
            :key="index"
            :style="
              i.result == 0 ? 'background-color: rgba(255,0,0,0.2);' : null
            "
          >
            <b>{{ index + 1 }}.</b> {{ i.name }}

            <br v-if="!(i.type == 1 || i.type == 2 || i.type == 3)" />
            <b
              >答案:
              <font :color="i.result != 0 ? 'green' : 'red'">{{
                i.resultAnswer
              }}</font>
            </b>
            &nbsp;
            <br v-if="!(i.type == 1 || i.type == 2 || i.type == 3)" />
            <b
              >标准答案:<font color="blue">{{ i.answer }}</font></b
            >
            <el-card
              v-for="(j, index) in i.content.choiceList"
              :key="index + '1'"
              :style="
                i.result == 0 ? 'background-color: rgba(255,0,0,0.05);' : null
              "
            >
              <b> {{ j.o }}.</b> {{ j.n }}</el-card
            >

            <br v-if="!(i.type == 1 || i.type == 2 || i.type == 3)" />
            <b v-if="!(i.type == 1 || i.type == 2 || i.type == 3)"
              >总分：{{ i.resultAllScore }}</b
            >

            <br v-if="!(i.type == 1 || i.type == 2 || i.type == 3)" />
            <input
              :max="i.resultAllScore"
              v-if="!(i.type == 1 || i.type == 2 || i.type == 3)"
              @focusout="focusout()"
              class="input"
              type="number"
              :oninput="
                'if(value>' + i.resultAllScore + ')value=' + i.resultAllScore
              "
            />
            <br />
            <b>解析：</b> <i>{{ i.analysis }}</i>
          </el-card>
          <el-button type="primary" @click="submit()">提交</el-button>

          <!-- <el-card v-for="(v,i) in questions2.arr" :key="i+'00'">
   
       <el-card v-for="(v2,i2) in v" :key="i2+'01'"  >{{v2.name}}</el-card>
   
  </el-card> -->
        </el-col>
      </el-row>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/exams/answers/answers.js";
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
      parameter: {},
      formParameter: {},
      row: {},
      answers: {},
      autoCorrectArray: {
        choiceQuestionAnswer: [],
        choiceQuestionId: [],
        choiceQuestionScore: [],
        multipleChoiceQuestionsAnswer: [],
        multipleChoiceQuestionsId: [],
        multipleChoiceQuestionsScore: [],
        TorFQuestionsAnswer: [],
        TorFQuestionsId: [],
        TorFQuestionsScore: [],
        completion: [],
        majorTopic1: [],
        majorTopic2: [],
        majorTopic3: [],
        completionScore: [],
        majorTopic1Score: [],
        majorTopic2Score: [],
        majorTopic3Score: [],
      },
      questions: [],
      score: 0,
      ortherScore: 0,
    };
  },
  methods: {
    //出题次数增加
    addHadQuestions() {
      api.addHadQuestions({questions:this.questions[0],answersid:this.row.id}, (response) => {




      });  
    },

    //提交事件
    submit() {
      console.log("submit----------");
      let parameter = {
        score: this.score + this.ortherScore,
        kid: this.row.kid,
        sid: this.row.sid,
      };
      this.addHadQuestions();

      api.markingSubmit(parameter, (response) => {
        this.$emit("flush");
        this.close();
      });
    },

    //输入框失去焦点事件
    focusout() {
      this.ortherScore = 0;
      var inputArray = document.getElementsByClassName("input");
      for (let index = 0; index < inputArray.length; index++) {
        this.ortherScore = this.ortherScore + Number(inputArray[index].value);
      }
      this.$refs.popup.open(
        "批卷" + "  当前得分:" + (this.score + this.ortherScore),
        "90%"
      );
    },
    //打开弹框
    open(parameter, title, disabled) {
      //初始化
      this.ortherScore = 0;
      this.score = 0;
      this.questions = [];
      this.autoCorrectArray = {
        choiceQuestionAnswer: [],
        choiceQuestionId: [],
        choiceQuestionScore: [],
        multipleChoiceQuestionsAnswer: [],
        multipleChoiceQuestionsId: [],
        multipleChoiceQuestionsScore: [],
        TorFQuestionsAnswer: [],
        TorFQuestionsId: [],
        TorFQuestionsScore: [],
        completion: [],
        majorTopic1: [],
        majorTopic2: [],
        majorTopic3: [],
        completionScore: [],
        majorTopic1Score: [],
        majorTopic2Score: [],
        majorTopic3Score: [],
      };

      this.row = parameter.row;
      console.log(parameter, "parameter");
      this.answers = JSON.parse(this.row.answer);

      this.$refs.popup.open("批卷" + "   自动判题得分:" + this.score, "90%");
      console.log(this.answers, "a$$$$$$$$$$$$$$$");
      this.$nextTick(() => {
        this.init();
      });
    },
    //关闭弹框
    close() {
      this.row = {};
      this.answers = {};
      this.disabled = false;

      this.$refs.popup.close();
    },
    //初始化
    init() {
      this.autoCorrect();
    },

    //自动批改选择判断
    autoCorrect() {
      //选择题位置
      var choiceQuestionPosition = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 1) {
          choiceQuestionPosition = index;
        }
      }
      if (choiceQuestionPosition != -1) {
        //选择题赋值
        if (this.answers.contents[choiceQuestionPosition] != undefined) {
          for (
            let index = 0;
            index < this.answers.contents[choiceQuestionPosition].qid.length;
            index++
          ) {
            this.answers.contents[choiceQuestionPosition].qid[index]
              .answer[0] == undefined
              ? (this.autoCorrectArray.choiceQuestionAnswer[index] = null)
              : (this.autoCorrectArray.choiceQuestionAnswer[index] =
                  this.answers.contents[choiceQuestionPosition].qid[
                    index
                  ].answer[0]);
            this.autoCorrectArray.choiceQuestionId[index] =
              this.answers.contents[choiceQuestionPosition].qid[index].id;
            this.autoCorrectArray.choiceQuestionScore[index] =
              this.answers.contents[choiceQuestionPosition].qscore[index];
          }
        }
      }

      //多选择题位置
      var multipleChoiceQuestionPosition = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 2) {
          multipleChoiceQuestionPosition = index;
        }
      }

      if (multipleChoiceQuestionPosition != -1) {
        //多选题答案赋值
        if (
          this.answers.contents[multipleChoiceQuestionPosition] != undefined
        ) {
          for (
            let index = 0;
            index <
            this.answers.contents[multipleChoiceQuestionPosition].qid.length;
            index++
          ) {
            //////////////
            var flag = "";
            var flagnull = 0;
            for (
              let i = 0;
              i <
              this.answers.contents[multipleChoiceQuestionPosition].qid[index]
                .answer.length;
              i++
            ) {
              if (flagnull == 0) {
                if (
                  this.answers.contents[multipleChoiceQuestionPosition].qid[
                    index
                  ].answer[i] != null
                ) {
                  flagnull = 1;
                  flag =
                    this.answers.contents[multipleChoiceQuestionPosition].qid[
                      index
                    ].answer[i];
                }
              } else {
                if (
                  this.answers.contents[multipleChoiceQuestionPosition].qid[
                    index
                  ].answer[i] != null
                ) {
                  flag =
                    flag +
                    "," +
                    this.answers.contents[multipleChoiceQuestionPosition].qid[
                      index
                    ].answer[i];
                }
              }
            }

            this.autoCorrectArray.multipleChoiceQuestionsAnswer[index] = flag;

            this.autoCorrectArray.multipleChoiceQuestionsId[index] =
              this.answers.contents[multipleChoiceQuestionPosition].qid[
                index
              ].id;

            this.autoCorrectArray.multipleChoiceQuestionsScore[index] =
              this.answers.contents[multipleChoiceQuestionPosition].qscore[
                index
              ];
          }
        }
      }

      //判断题赋值
      var TorFQuestionPosition = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 3) {
          TorFQuestionPosition = index;
        }
      }
      if (TorFQuestionPosition != -1) {
        //判断题赋值
        if (this.answers.contents[TorFQuestionPosition] != undefined) {
          for (
            let index = 0;
            index < this.answers.contents[TorFQuestionPosition].qid.length;
            index++
          ) {
            this.answers.contents[TorFQuestionPosition].qid[index].answer[0] ==
            undefined
              ? (this.autoCorrectArray.TorFQuestionsAnswer[index] = 5)
              : (this.autoCorrectArray.TorFQuestionsAnswer[index] =
                  this.answers.contents[TorFQuestionPosition].qid[
                    index
                  ].answer[0]);
            this.autoCorrectArray.TorFQuestionsId[index] =
              this.answers.contents[TorFQuestionPosition].qid[index].id;
            this.autoCorrectArray.TorFQuestionsScore[index] =
              this.answers.contents[TorFQuestionPosition].qscore[index];
          }
        }
      }
      //填空
      var completionPosition = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 4) {
          completionPosition = index;
        }
      }
      if (completionPosition != -1) {
        for (
          let index = 0;
          index < this.answers.contents[completionPosition].qid.length;
          index++
        ) {
          this.autoCorrectArray.completion[index] =
            this.answers.contents[completionPosition].qid[index].id;
          this.autoCorrectArray.completionScore[index] =
            this.answers.contents[completionPosition].qscore[index];
        }
      }

      //555555
      var majorTopicPosition1 = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 5) {
          majorTopicPosition1 = index;
        }
      }

      if (majorTopicPosition1 != -1) {
        for (
          let index = 0;
          index < this.answers.contents[majorTopicPosition1].qid.length;
          index++
        ) {
          this.autoCorrectArray.majorTopic1[index] =
            this.answers.contents[majorTopicPosition1].qid[index].id;
          this.autoCorrectArray.majorTopic1Score[index] =
            this.answers.contents[majorTopicPosition1].qscore[index];
        }
      }
      //666666666
      var majorTopicPosition2 = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 6) {
          majorTopicPosition2 = index;
        }
      }
      if (majorTopicPosition2 != -1) {
        for (
          let index = 0;
          index < this.answers.contents[majorTopicPosition2].qid.length;
          index++
        ) {
          this.autoCorrectArray.majorTopic2[index] =
            this.answers.contents[majorTopicPosition2].qid[index].id;
          this.autoCorrectArray.majorTopic2Score[index] =
            this.answers.contents[majorTopicPosition2].qscore[index];
        }
      }

      //7777777
      var majorTopicPosition3 = -1;
      for (let index = 0; index < this.answers.types.length; index++) {
        if (this.answers.types[index] == 7) {
          majorTopicPosition3 = index;
        }
      }
      if (majorTopicPosition3 != -1) {
        for (
          let index = 0;
          index < this.answers.contents[majorTopicPosition3].qid.length;
          index++
        ) {
          this.autoCorrectArray.majorTopic3[index] =
            this.answers.contents[majorTopicPosition3].qid[index].id;
          this.autoCorrectArray.majorTopic3Score[index] =
            this.answers.contents[majorTopicPosition3].qscore[index];
        }
      }
      console.log(this.autoCorrectArray);
      //异步请求
      api.autoCorrect(this.autoCorrectArray, (response) => {
        this.$set(this.questions, 0, response.data);
        //this.questions=response.data;
        for (let index = 0; index < this.questions[0].length; index++) {
          this.score = this.score + this.questions[0][index].resultScore;

          this.questions[0][index].content = JSON.parse(
            this.questions[0][index].content
          );
        }
        this.$refs.popup.open("批卷" + "   自动判题得分:" + this.score, "90%");
        console.log(this.questions[0], "questions");
      });
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
