<!-- 填空题组件 -->
<template>
  <div>
    <!-- 题号  题目 -->
    <div>
      <b>{{ questionNumber }}</b
      ><b>{{ title }}</b>
    </div>
    <!-- 填空位置 -->
    <div v-for="(item, index) in spaceNumber" :key="item">
      <el-input placeholder="请输入答案" v-model="answerArr[index]">
        <template slot="prepend">{{ item }}</template>
      </el-input>
    </div>
  </div>
</template>

<script>
export default {
  name: "FillInTheBlanks",
  data() {
    return {
      spaceNumber: 0, //填答案的空格数量
      answerArr: [], //答案的数组
    };
  },
  props: {
    // 题号
    questionNumber: {
      type: String,
      default() {
        return "没有收到题号";
      },
    },
    // 题目
    title: {
      type: String,
      default() {
        return "没有收到题目";
      },
    },
  },
  methods: {},
  created() {
    // 获取填空数量
    this.spaceNumber = this.title.match(/{_}/g).length;
    this.answerArr = new Array(this.spaceNumber);
  },
  watch: {
    answerArr: {
      immediate: true,
      handler(newVal) {
        this.$emit("answer", newVal);
      },
    },
  },
};
</script>

<style lang="scss" scoped>
</style>