<template>
  <div>
    <el-row>
      <el-col :span="24">
        <span>{{ parameter[`name${col}`] }}</span>
      </el-col>
    </el-row>
    <el-button
      type="primary"
      size="mini"
      icon="el-icon-plus"
      v-if="ifadd"
      @click="add(parameter, col, messageId)"
    ></el-button>
     <!-- v-if="ifedit" -->
    <el-button
      type="primary"
      size="mini"
      icon="el-icon-edit"
     
      @click="edit(parameter, col, messageId)"
    ></el-button>
     <!-- v-if="ifremove" -->
    <el-button
      type="danger"
      size="mini"
      icon="el-icon-delete"
     
      @click="remove(parameter, col)"
    ></el-button>
    <el-button
      size="mini"
      icon="el-icon-arrow-up"
      v-if="ifup"
      @click="upOrDown(-1, parameter, col)"
    ></el-button>
    <el-button
      size="mini"
      icon="el-icon-arrow-down"
      v-if="ifdown"
      @click="upOrDown(1, parameter, col)"
    ></el-button>
    <edit2 ref="edit2"></edit2>
  </div>
</template>

<script>
import edit2 from "./edit2.vue";
import apiy from "@/api/edu/subject/subject.js";
import betterDebounce from "@/util/betterDebounce.js";
export default {
  props: {
    parameter: {
      type: Object,
    },
    col: {
      type: Number,
    },
    coltitle: {
      type: String,
    },
    scope: {
      type: Object,
    },
    messageId: {
      type: String,
    },
  },
  data() {
    return {
      ifadd: false,
      ifedit: false,
      ifremove: false,
      ifup: false,
      ifdown: false,
    };
  },
  components: {
    edit2,
  },
  mounted() {
    this.showBtn();
    // console.log(this.parameter)
  },
  updated() {
    this.showBtn();
  },
  methods: {
    add(parameter, col, messageId) {
      let editParameter = {
        parameter: parameter,
        col: col,
        subjectid: messageId,
      };
      if (col == 2) {
        this.$refs.edit2.open(editParameter, "添加-" + this.coltitle);
      } else {
        this.$refs.edit.open(editParameter, "添加-" + this.coltitle);
      }
    },
    edit(parameter, col, messageId) {
      let editParameter = {
        parameter: parameter,
        col: col,
        subjectid: messageId,
      };
      if (col == 2) {
        this.$refs.edit2.open(editParameter, "编辑-" + this.coltitle);
      } else {
        this.$refs.edit.open(editParameter, "编辑-" + this.coltitle);
      }
    },
    remove(parameter, col) {
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        let optId = parameter[`id${col}`];
        this.$eventBus.$emit("rmvEvent2", optId);
      });
    },
    upOrDown(isUp, parameter, col) {
      let page = { isUp: isUp, optId: parameter[`id${col}`], col: col };
      this.$eventBus.$emit("updownEvent2", page);
    },
    showBtn() {
      if (typeof this.parameter[`name${this.col}`] !== "undefined") {
        if (this.col == 3) {
          this.ifadd = false;
          this.ifedit = false;
          this.ifremove = false;
          this.ifup = false;
          this.ifdown = false;
        } else {
          this.ifadd = true;
          this.ifedit = true;
          this.ifremove = true;
          this.ifup = true;
          this.ifdown = true;
        }
      } else if (typeof this.parameter[`name${this.col}`] == "undefined") {
        if (this.col == 3) {
          this.ifadd = false;
          this.ifedit = false;
          this.ifremove = false;
          this.ifup = false;
          this.ifdown = false;
        } else {
          this.ifadd = true;
          this.ifedit = false;
          this.ifremove = false;
          this.ifup = false;
          this.ifdown = false;
        }
      }
    },
  },
  watch: {
    parameter(val) {
      this.parameter = val;
    },
    messageId(val) {
      this.messageId = val;
    },
  },
};
</script>
<style lang="scss" scoped="scoped">
.el-button {
  padding: 5px !important;
  margin: 1px !important;
}
</style>
