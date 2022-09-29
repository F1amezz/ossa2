<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-table
        class="table"
        :data="records"
        stripe
        border
        height="auto"
        @selection-change="selectionChange"
      >
        <!-- v-loading="listLoading" -->
        <el-table-column prop="name" label="学生名字"></el-table-column>
        <el-table-column prop="sex" label="性别">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.sex == 2" size="mini" type="danger"
              >女</el-tag >
            <el-tag v-else="scope.row.sex == 1" size="mini">男</el-tag>
           
          </template>
        </el-table-column>
        <el-table-column prop="nativeplace" label="籍贯"></el-table-column>
        <el-table-column prop="professional" label="专业"></el-table-column>
        <el-table-column prop="lname" label="登录名"></el-table-column>
        <el-table-column prop="pwd" label="密码"></el-table-column>
        <el-table-column prop="mobile" label="手机号码"></el-table-column>
        <el-table-column prop="school" label="学校"></el-table-column>
        <el-table-column prop="cometime" label="报到时间"></el-table-column>
        <el-table-column prop="state" label="状态">
           <template slot-scope="scope">
            <span v-if="scope.row.state == 2">休学</span>
            <span v-if="scope.row.state == 3">毕业</span>
            <span v-if="scope.row.state == 4">待报到</span>
            <div v-else="scope.row.state == 1">在读</div>
          </template>
        </el-table-column>
        <el-table-column prop="cid" label="班级"></el-table-column>
      </el-table>
      <el-tag>本次成功导入数据{{ records.length }}条。</el-tag>
    </div>
    <div slot="footer">
      <el-button
        v-if="!disabled"
        type="primary"
        :loading="confirmLoading"
        @click="confirm()"
        >确定导入</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/stus/student/student.js";

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
      records: {},
      rules: {},
    };
  },
  methods: {
    // listLoading() {},
    selectionChange() {},
    //打开弹框
    open(parameter, title, disabled, size) {
      this.records = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, "88%");
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },
    //提交表单
    confirm() {
      this.confirmLoading = true;

      api.imports(
        this.records,
        (res) => {
          this.confirmLoading = false;
          this.$utils.msg.success("导入成功");
          this.close();
          // 刷新父页面
          this.$emit("flush");
        },
        (err) => {
          this.$utils.msg.warning("添加学生班级未创建");
          this.confirmLoading = false;
        }
      );
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
