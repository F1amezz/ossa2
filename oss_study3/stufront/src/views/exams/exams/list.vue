<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="试卷名称">
              <el-input v-model="searchParams.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="考试类型">
              <select-option-dictionary v-model="searchParams.extype" @change="search()" dkey="exp-type">
              </select-option-dictionary>
            </el-form-item>
          </el-col>

          <el-collapse-transition>
            <div v-if="isShowMoreSearch">
              <!--隐藏区域-->
            </div>
          </el-collapse-transition>
          <div class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button @click="clearSearchVal">清空</el-button>
            <el-button type="text" v-if="!isShowMoreSearch" @click="isShowMoreSearch = !isShowMoreSearch">
              更多
              <i class="el-icon-arrow-down"></i>
            </el-button>
            <el-button type="text" v-else @click="isShowMoreSearch = !isShowMoreSearch">
              收起
              <i class="el-icon-arrow-up"></i>
            </el-button>
          </div>
        </el-row>
      </el-form>
    </div>

    <div class="dataTable autoFlex" v-if="listData">
      <el-table class="table" :data="listData" stripe border height="auto" @selection-change="selectionChange"
        v-loading="listLoading">
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <!-- <el-table-column prop="cid" label="考试班级id"></el-table-column> -->
        <el-table-column prop="name" label="试卷名称"></el-table-column>
        <el-table-column prop="extype" label="考试类型">
          <template slot-scope="scope">
            <p v-if="scope.row.extype == 1">小测</p>
            <p v-if="scope.row.extype == 2">周测</p>
            <p v-if="scope.row.extype == 3">阶段测</p>
          </template>
        </el-table-column>
        <el-table-column label="得分/及格分/总分">
          <template slot-scope="scope">
            <el-col>
              <el-tag type="danger" v-if="scope.row.anscore < scope.row.passScore">{{ scope.row.anscore }}</el-tag>
              <el-tag v-if="scope.row.anscore > scope.row.passScore">{{ scope.row.anscore }}</el-tag>
              / <el-tag type="info">{{ scope.row.passScore }}</el-tag>
              / <el-tag type="info">{{ scope.row.exscores }}</el-tag>
            </el-col>
          </template>
        </el-table-column>

        <el-table-column label="开始时间/截止时间">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.startTime }}</el-tag>
            <el-tag type="danger">{{ scope.row.endTime }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button @click="toStartTest(scope.$index, scope.row)"
                v-bind:disabled="scope.row.state == 0 ? false : true">开始考试</el-button>
              <!-- <el-button @click="toHistory(scope.$index, scope.row)"
                v-bind:disabled="scope.row.lookState == 0 ? true : false">历史记录</el-button> -->
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBar">
        <el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
          :page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"></el-pagination>
      </div>
    </div>
    <edit ref="edit"></edit>
    <startTest ref="startTest"  @flush="list()"></startTest>
  </div>
</template>
<script>
import startTest from "./startTest.vue";
import edit from "./edit.vue";
import imageUpload from "@/components/biz/imageUpload/imageUpload.vue";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
import api from "@/api/exams/exams/exams.js";
export default {
  name: "exams",

  components: {
    startTest,
    edit,
    imageUpload,
    selectOptionDictionary,
  },
  data() {
    return {
      startTest: false,
      name: "",
      extype: "",
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
    };
  },
  watch: {
    "searchParams.name": function () {
      this.search();
    },
  },

  methods: {


    //获取数据
    // list() {
    //   this.listLoading = true;
    //   let params = this.$utils.merger(this.searchParams, this.page);
    //   api.list(params, (response) => {
    //     this.listData = response.data;
    //     console.log(response.data, "-----------");
    //     this.listLoading = false;
    //   });
    // },

    //获取考试结果信息
    list() {

      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        console.log(response.data, "-----------");
        this.listLoading = false;
      });
    },
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加");
    },
    //去编辑
    toHistory(index, row) {
      let editParameter = {
        id: row,
      };
      this.$refs.edit.open(editParameter, "编辑");
    },
    //去详情
    toDetail(index, row) {
      let detailParameter = {
        id: row.id,
      };
      this.$refs.edit.open(detailParameter, "详情", true);
    },
    //去考试
    toStartTest(index, row) {
      api.saveAnswers(row, (response) => {
        if (response.data < 0) {
          this.$utils.msg.warning(response.msg);
        } else {
          let parameter = {
            row: row,
            timeDown: response.data,
          };
          ///////////////////////////////////////
          this.$refs.startTest.open(parameter, "考试");
          this.$utils.msg.success(response.msg);
        }
      });
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
    //批量删除
    batchRemove() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      let parameter = {
        ids: this.selected,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          {
            ids: this.selected,
          },
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
    //改变选择项
    selectionChange(val) {
      //清空
      this.selected = [];
      for (let item of val) {
        this.selected.push(item.id);
      }
    },
    //改变每页显示数量
    sizeChange(val) {
      this.page.size = val;
      this.list();
    },
    //改变现在的页码
    currentChange(val) {
      this.page.current = val;
      this.list();
    },

    //搜索
    search() {
      this.list();
    },
    //清空搜索框
    clearSearchVal() {
      this.searchParams = {};
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

