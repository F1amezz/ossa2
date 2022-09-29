<template>
  <div class="tab-body">
    <el-row class="autoFlex" :gutter="10" v-if="popupLoading">
      <el-col :span="4">
        <el-card class="leftCard">
          <div slot="header" class="clearfix">
            <span>模块列表</span>
            <el-button
              @click="expandTree"
              class="expandBtn"
              type="primary"
              icon="el-icon-s-fold"
              size="mini"
              style="margin-left: 20px; float: right"
            ></el-button>
          </div>
          <tree
            v-loading="treeLoading"
            ref="permissionTree"
            :items="permissionList"
            :defaultExpandAll="treeExpand"
            :defaultParent="0"
            @nodeClick="treeClick"
          ></tree>
        </el-card>
      </el-col>
      <el-col :span="20">
        <el-card class="rightCard">
          <div slot="header" class="clearfix">
            <span>{{ Subflag }}</span
            ><el-button
              size="mini"
              type="primary"
              icon="el-icon-plus"
              @click="toSave()"
              style="margin-left: 30px"
            >
              添加</el-button
            >
          </div>
          <div class="operator"></div>
          <div class="dataTable autoFlex">
            <qlh-table
              class="table"
              style="height: auto"
              :data="permissionTable"
              stripe
              border
              height="auto"
              :excelProp="excelProp"
            >
              <qlh-table-column
                prop="name"
                :label="Subflag + '名'"
                min-width="130"
                :width="322"
                :editable="false"
                sortable
              >
              </qlh-table-column>
              <qlh-table-column
                prop="days"
                :label="Subflag + '天数'"
                min-width="100"
                :width="110"
                :editable="false"
                sortable
              >
              </qlh-table-column>
              <qlh-table-column prop="remk" label="备注"></qlh-table-column>
              <qlh-table-column
                prop="url"
                :label="'视频'"
                :width="80"
              >
                <template slot-scope="scope">
                  <el-tag size="mini" v-if="scope.row.url == '[]'">无</el-tag>

                  <el-tag size="mini" v-else @click="showUrl(scope.row.url)"
                    >查看</el-tag
                  >
                </template>
              </qlh-table-column>
              <qlh-table-column
                prop="level"
                label="等级"
                width="80"
                :formatter="
                  (row, column) => {
                    return row.level == 1
                      ? '容易'
                      : row.level == 2
                      ? '一般'
                      : row.level == 3
                      ? '困难'
                      : row.level == 0
                      ? '其他'
                      : '';
                  }
                "
                sortable
              ></qlh-table-column>
              <qlh-table-column
                label="操作"
                fixed="right"
                width="300"
                ignored
                :editable="false"
              >
                <template slot-scope="scope">
                  <div class="dataTable-operator">
                    <el-button
                      size="mini"
                      @click="toExam(scope.$index, scope.row)"
                      >试卷</el-button
                    >
                    <el-button
                      size="mini"
                      @click="toDetail(scope.$index, scope.row)"
                      >详情
                    </el-button>
                    <el-button
                      size="mini"
                      @click="toUpdate(scope.$index, scope.row)"
                      >编辑
                    </el-button>
                    <el-button
                      size="mini"
                      type="danger"
                      @click="remove(scope.$index, scope.row)"
                      >删除
                    </el-button>
                  </div>
                </template>
              </qlh-table-column>
            </qlh-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div>
      <el-dialog
        title="课程视频地址查看"
        :visible.sync="dialogVisible"
        width="42%"
      >
        <span :key="index" v-for="(tag, index) in urlList">
          <el-tag size="medium" type="success">{{ tag.k }}</el-tag>
          <el-tag size="medium">{{ tag.v }}</el-tag><br />
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
    <edit ref="edit"></edit>
    <exam ref="exam" v-if="!popupLoading"></exam>
  </div>
</template>
<script>
import edit from "./edit.vue";
import exam from "./showExampaper.vue";
import tree from "@/components/tree/tree.vue";
import api from "@/api/edu/subject/subject.js";
import api2 from "@/api/exams/exampaper/exampaper.js";
import qlhTable from "@/components/qlhTable/qlhTable.vue";
import qlhTableColumn from "@/components/qlhTable/qlhTableColumn.vue";
import MyCom from "@/components/myCom2/myCom.vue";
export default {
  name: "course",
  components: {
    MyCom,
    tree,
    edit,
    exam,
    qlhTable,
    qlhTableColumn,
  },
  data() {
    return {
      Subflag: "模块",
      dialogVisible: false,
      popupLoading: true,
      treeLoading: false,
      treeRef: "permissionTree",
      treeExpand: false,
      permissionList: [],
      urlList: [],
      currentNode: {},
      permissionTable: [],
      tableLoading: true,
      editParameter: {},
      excelProp: {
        excelName: "科目信息",
        sheetname: "科目信息",
      },
    };
  },
  methods: {
    showUrl(t) {
      this.urlList = JSON.parse(t);
      this.dialogVisible = true;
    },
    //获取数据
    list() {
      this.treeLoading = true;
      api.list({}, (response) => {
        // this.urlList = JSON.parse(response.data.url);
        this.permissionList = response.data;
        this.treeLoading = false;
        this.freshTable();
      });
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
      this.Subflag = "科目";
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
    //去添加
    toSave() {
      let parameter = {
        pid: this.currentNode.id != undefined ? this.currentNode.id : 0,
        level:
          this.currentNode.level != undefined ? this.currentNode.level + 1 : 0,
        code: this.currentNode.code,
      };

      this.$refs.edit.open(parameter, "添加");
    },
    //去编辑
    toUpdate(index, row) {
      let parameter = {
        id: row.id,
      };
      this.$refs.edit.open(parameter, "编辑");
    },
    //去详情
    toDetail(index, row) {
      let detailParameter = {
        id: row.id,
      };
      this.$refs.edit.open(detailParameter, "详情", true);
    },
    //去详情
    toExam(index, row) {
      let parameter = {
        kid: row.id,
      };
      setTimeout(() => {
        this.$refs.exam.list(parameter);
      }, 5);
      this.popupLoading = false;
    },
    //删除
    remove(index, row) {
      let parameter = {
        ids: row.id,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          parameter,
          (response) => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
          }
        );
      });
    },
  },

  mounted() {
    this.list();
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>