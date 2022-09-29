<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="100px">
        <el-row>
         <el-col :span="4">
            <el-form-item label="公司名称">
              <el-input v-model="searchParams.cname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="岗位名称">
              <el-input v-model="searchParams.pname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="状态">
              <el-select v-model="searchParams.state" @change="search" placeholder="请选择">
                <el-option
                  v-for="item in ['全部','有效','失效']"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-collapse-transition>
            <div v-if="isShowMoreSearch">
              <!--隐藏区域-->
            </div>
          </el-collapse-transition>
          <div class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="search"
              >搜索</el-button
            >
            <el-button @click="clearSearchVal">清空</el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table
        class="table"
        :data="listData.records"
        stripe
        border
        height="auto"
        @selection-change="selectionChange"
        v-loading="listLoading"
      >
        <el-table-column prop="cname" width="180" label="岗位名称">
          <template slot-scope="scope">
            <el-tag size="mini" type="info">{{
              scope.row.cname
            }}</el-tag>
            <el-tag type="success" size="mini">{{ scope.row.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bgntm" width="110" label="有效期">
          <template slot-scope="scope">
            <el-tag size="mini" type="success">{{ scope.row.bgntm }}</el-tag>
            <el-tag type="danger" size="mini">{{ scope.row.endtm }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          width="90"
          prop="maxneed"
          label="招聘人数"
        ></el-table-column>

        <el-table-column width="90" prop="maxneed" label="offer/面试">
          <template slot-scope="scope">
            {{ scope.row.offered?scope.row.offered:0 }} / {{ scope.row.mtimes }}
          </template>
        </el-table-column>
        <el-table-column
          width="90"
          prop="exps"
          label="经验(年)"
        ></el-table-column>
        <el-table-column width="90" prop="xueli" label="学历"></el-table-column>
        <el-table-column
          width="130"
          prop="sarry"
          label="待遇"
        ></el-table-column>
        <el-table-column prop="tech" label="技术要求"></el-table-column>

        <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button size="mini" @click="toDetail(scope.$index, scope.row)"
                >详情</el-button
              >
              <el-button size="mini" @click="toUpdate(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="remove(scope.$index, scope.row)"
                >删除</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBar">
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :total="listData.total"
          :page-size="page.size"
          :current-page="page.current"
          :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"
        ></el-pagination>
      </div>
    </div>
    <edit ref="edit"></edit>
  </div>
</template>
<script>
import edit from "./edit.vue";
import api from "@/api/interview/posts/posts.js";
export default {
  name: "posts",
  components: {
    edit,
  },
  data() {
    return {
      searchParams: {state:'有效'},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
    };
  },
  methods: {
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        console.log(this.listData);
        this.listLoading = false;
      });
    },
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加就业企业");
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
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
      this.searchParams = {state:'有效'};
       this.list();
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

