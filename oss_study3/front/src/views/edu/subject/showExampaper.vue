<template>
  <div class="tab-body">
    <!-- <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="姓名">
              <el-input v-model="searchParams.name"></el-input>
            </el-form-item>
          </el-col>

          <el-collapse-transition>
            <div v-if="isShowMoreSearch">
            </div>
          </el-collapse-transition>
          <div class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="search"
              >搜索</el-button
            >
            <el-button @click="clearSearchVal">清空</el-button>
            
            <el-button
              type="text"
              v-if="!isShowMoreSearch"
              @click="isShowMoreSearch = !isShowMoreSearch"
            >
              更多
              <i class="el-icon-arrow-down"></i>
            </el-button>
            <el-button
              type="text"
              v-else
              @click="isShowMoreSearch = !isShowMoreSearch"
            >
              收起
              <i class="el-icon-arrow-up"></i>
            </el-button>
          </div>
        </el-row>
      </el-form>
    </div> -->
    <div class="operator">
      <!-- <el-button type="primary" icon="el-icon-plus" @click="toSave()"
        >添加</el-button
      >
      <el-button type="danger" icon="el-icon-delete" @click="batchRemove()"
        >批量删除</el-button
      > -->
      <el-button @click="goback">返回</el-button>
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table
        class="table"
        :data="listData"
        stripe
        border
        height="auto"
        v-loading="listLoading"
      >
        <el-button @click="goback">返回</el-button>
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
        ></el-table-column>
        <el-table-column prop="name" label="试卷名称"></el-table-column>
        <el-table-column
          prop="duration"
          label="考试时间（分钟）"
        ></el-table-column>
        <el-table-column prop="score" label="总分"></el-table-column>
        <el-table-column prop="passScore" label="及格分数"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <!-- <el-table-column prop="isVisible" label="是否可见 1-可见、2-不可见"></el-table-column> -->
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="mark" label="备注"></el-table-column>
        <!-- <el-table-column prop="cid" label="课程id"></el-table-column> -->
        <!-- <el-table-column prop="mid" label="模块id"></el-table-column> -->
        <!-- <el-table-column prop="kid" label="科目id"></el-table-column> -->
        <!--<el-table-column prop="crtm" label="创建时间"></el-table-column>
        <el-table-column prop="mdtm" label="修改时间"></el-table-column>
        <el-table-column prop="mder" label="修改人"></el-table-column>
        <el-table-column prop="crer" label="创建人"></el-table-column>
         <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button
                v-has-permission-code-and="''"
                size="mini"
                icon="el-icon-search"
                @click="toDetail(scope.$index, scope.row)"
                >详情</el-button
              >
              <el-button
                v-has-permission-code-and="''"
                size="mini"
                icon="el-icon-edit"
                @click="toUpdate(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                v-has-permission-code-and="''"
                size="mini"
                icon="el-icon-delete"
                type="danger"
                @click="remove(scope.$index, scope.row)"
                >删除</el-button
              >
            </div>
          </template>
        </el-table-column> -->
      </el-table>
      <!-- <div class="pageBar">
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :total="listData.total"
          :page-size="page.size"
          :current-page="page.current"
          :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"
        ></el-pagination>
      </div> -->
    </div>
  </div>
</template>
<script>
import api from "@/api/exams/exampaper/exampaper.js";
export default {
  name: "exampaper",
  components: {},
  data() {
    return {
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
    };
  },
  methods: {
    //获取数据
    list(kid) {
      this.listLoading = true;
      //   let parameter = {
      //       kid: this.parameter.id,
      //     };
      //   alert(kid);
      api.list2(kid, (response) => {
        this.listData = response.data;
        this.listLoading = false;
      });
    },
    goback() {
      this.$parent.popupLoading = true;
      this.$parent.list();
    },
    //去添加
    // toSave() {
    //   this.$refs.edit.open({}, "添加");
    // },
    // //去编辑
    // toUpdate(index, row) {
    //   let editParameter = {
    //     id: row.id,
    //   };
    //   this.$refs.edit.open(editParameter, "编辑");
    // },
    // //去详情
    // toDetail(index, row) {
    //   let detailParameter = {
    //     id: row.id,
    //   };
    //   this.$refs.edit.open(detailParameter, "详情", true);
    // },
    // //删除
    // remove(index, row) {
    //   let parameter = {
    //     ids: row.id,
    //   };
    //   this.$utils.confirm.warning("提示", "确定删除吗？", () => {
    //     api.remove(
    //       parameter,
    //       (response) => {
    //         this.$utils.msg.success(response.msg);
    //         this.list();
    //       },
    //       (response) => {
    //         this.$utils.msg.warning(response.msg);
    //       }
    //     );
    //   });
    // },
    // //批量删除
    // batchRemove() {
    //   if (!this.selected || this.selected.length == 0) {
    //     this.$utils.msg.info("请选择至少一行");
    //     return;
    //   }
    //   let parameter = {
    //     ids: this.selected,
    //   };
    //   this.$utils.confirm.warning("提示", "确定删除吗？", () => {
    //     api.remove(
    //       {
    //         ids: this.selected,
    //       },
    //       (response) => {
    //         this.$utils.msg.success(response.msg);
    //         this.list();
    //       },
    //       (response) => {
    //         this.$utils.msg.warning(response.msg);
    //       }
    //     );
    //   });
    // },
    // //改变选择项
    // selectionChange(val) {
    //   //清空
    //   this.selected = [];
    //   for (let item of val) {
    //     this.selected.push(item.id);
    //   }
    // },
    // //改变每页显示数量
    // sizeChange(val) {
    //   this.page.size = val;
    //   this.list();
    // },
    // //改变现在的页码
    // currentChange(val) {
    //   this.page.current = val;
    //   this.list();
    // },

    // //搜索
    // search() {
    //   this.list();
    // },
    // //清空搜索框
    // clearSearchVal() {
    //   this.searchParams = {};
    // },
  },
  mounted() {
    this.list();
  },
  created() {},
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>

