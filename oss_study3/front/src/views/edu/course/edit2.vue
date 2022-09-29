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
          <el-col :span="24">
            <el-form-item label="" prop="sid">
              <div style="margin: 15px 0"></div>
              <tree
                v-loading="treeLoading"
                ref="permissionTree"
                :items="permissionList"
                :defaultExpandAll="treeExpand"
                :defaultParent="0"
                @nodeClick="treeClick"
              ></tree>
              <!-- <el-checkbox-group
                v-model="a"
                @change="handleCheckedCitiesChange"
              >
                <el-checkbox
                  v-for="city in formParameterAll"
                  :label="city.id"
                  :key="city.id"
                  >{{ city.name }}</el-checkbox
                >
              </el-checkbox-group> -->
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button
        v-if="!disabled"
        type="primary"
        :loading="confirmLoading"
        @click="confirm()"
        >确 定</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import tree from "@/components/tree/tree.vue";
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/edu/course/course.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "edit",
  components: {
    tree,
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      treeLoading: false,
      treeRef: "permissionTree",
      treeExpand: false,
      permissionList: [],
      currentNode: {},
      permissionTable: [],
      tableLoading: true,
      editParameter: {},
      checkAll: false,
      a: [],
      // cities: cityOptions,
      isIndeterminate: false,
      formParameterAll: {},
      checkedCities: {},
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      rules: {},
    };
  },
  methods: {
    handleCheckAllChange(val) {
      let array = [];
      for (let j of this.formParameterAll) {
        array.push(j.id);
      }
      this.a = val ? array : [];

      this.checkAll = val ? true : false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.formParameterAll.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.formParameterAll.length;
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

    expandTree() {
      this.treeExpand = !this.treeExpand;
      //控制el-tree展开收起
      for (
        var i = 0;
        i < this.$refs.permissionTree.getTree().store._getAllNodes().length;
        i++
      ) {
        this.$refs.permissionTree.getTree().store._getAllNodes()[i].expanded =
          this.treeExpand;
      }
    },
    //点击部门
    treeClick(data) {
      if (this.currentNode == data) {
        this.currentNode = {};
        $(".el-tree-node").removeClass("is-current");
      } else {
        this.currentNode = data;
      }
      this.freshTable();
    },
    freshTable() {
      this.tableLoading = true;
      this.permissionTable = [];
      if (this.currentNode.id) {
        for (var d of this.permissionList) {
          if (d.pid == this.currentNode.id) {
            this.permissionTable.push(d);
          }
        }
      } else {
        for (var d of this.permissionList) {
          //根节点
          if (d.pid == 0) {
            this.permissionTable.push(d);
          }
        }
      }
      this.tableLoading = false;
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
          //编辑
          this.formParameter.sid = this.a;
          let parameter = this.$utils.merger(this.formParameter);
          api.update2(
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
          id: this.parameter.id,
        };
        api.toUpdateSubject2(parameter, (response) => {
          this.permissionList = response.data.subjectsAll;
          this.popupLoading = false;
          this.formParameter.id = this.parameter.id;
        });
      } else {
        api.toSave({}, (response) => {
          console.log(response.data);
          this.permissionList = response.data.subjectsAll;
        });
        //添加
        this.popupLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
