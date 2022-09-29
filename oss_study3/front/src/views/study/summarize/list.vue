<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="4">
            <el-form-item label="姓名">
              <el-input v-model="searchParams.sname" @click="search"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="状态">
              <el-select v-model="state" @change="search" placeholder="请选择">
                <el-option v-for="(i) in ['未读','已读','全部']" :key="i" :label="i" :value="i"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="班级">
              <el-select v-model="searchParams.cname" placeholder="请选择班级">
                <el-option
                  v-for="item in clazzList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name"
                ></el-option>
              </el-select>
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
            <el-button
              type="text"
              v-if="!isShowMoreSearch"
              @click="isShowMoreSearch = !isShowMoreSearch"
            >
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
    <div class="operator">
      <el-button type="primary" icon="el-icon-plus" @click="toSave()"  v-has-permission-code-and="'002233001'">添加</el-button>
      <el-button type="primary" icon="el-icon-finished" @click="batchUpdateReadst()">批量已读</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="batchRemove()" v-has-permission-code-and="'002233006'">批量删除</el-button>
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
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="subtime" label="提交时间"></el-table-column>
        <el-table-column prop="sname" label="学生"></el-table-column>
        <el-table-column prop="cname" label="班级"></el-table-column>
        <el-table-column prop="tname" label="老师"></el-table-column>
        <el-table-column prop="a1" label="讲授语速">
          <template slot-scope="scope"><el-tag>{{scope.row.a1}}</el-tag></template>   
        </el-table-column>
        <el-table-column prop="a2" label="课堂案例">
          <template slot-scope="scope"><el-tag >{{scope.row.a2}}</el-tag></template> 
        </el-table-column>
        <el-table-column prop="a3" label="答疑解忧">
          <template slot-scope="scope"><el-tag >{{scope.row.a3}}</el-tag></template> 
        </el-table-column>
        <el-table-column prop="a4" label="听课情况">
          <template slot-scope="scope"><el-tag >{{scope.row.a4}}</el-tag></template> 
        </el-table-column>
        <el-table-column prop="a5" label="练习完成">
          <template slot-scope="scope"><el-tag >{{scope.row.a5}}</el-tag></template> 
        </el-table-column>
        <el-table-column prop="a6" label="讲师技能">
          <template slot-scope="scope"><el-tag>{{scope.row.a6}}</el-tag></template> 
        </el-table-column>
        <el-table-column prop="chetime" label="老师审阅时间"></el-table-column>
        <el-table-column prop="readst" label="已读状态">
          <template slot-scope="scope">
            <el-switch v-if="scope.row.readst == 1" :disabled="true" :value="true"></el-switch>
            <el-switch
              v-if="scope.row.readst == 0"
              :disabled="false"
              @change="updateReadst(scope.$index, scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="250px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button
                size="mini"
                icon="el-icon-search"
                @click="toDetail(scope.$index, scope.row)"
              >详情</el-button>
              <el-button
                size="mini"
                icon="el-icon-edit"
                @click="toUpdate(scope.$index, scope.row)"
                v-has-permission-code-and="'002233005'"
              >编辑</el-button>
              <el-button
                size="mini"
                icon="el-icon-delete"
                type="danger"
                @click="remove(scope.$index, scope.row)"
                v-has-permission-code-and="'002233006'"
              >删除</el-button>
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
    <edit3 ref="edit3"></edit3>
  </div>
</template>
<script>
import edit from "./edit.vue";
import edit3 from "./edit3.vue";
import api from "@/api/study/summarize/summarize.js";
export default {
  name: "summarize",
  components: {
    edit,
    edit3
  },
  data() {
    return {
      state: "未读",
      searchParams: { readst: 0 },
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      postsList: [],
      page: new this.$constant.pageObj(),
      clazzList: {}
    };
  },
  methods: {
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, response => {
        this.listData = response.data;
        this.listLoading = false;
      });
    },
    //获取班级
    listCname() {
      this.listLoading = true;

      api.listCname({}, response => {
        this.postsList = response.data;
        this.clazzList = response.data.clazzList;
        console.log('response.data.clazzList: ', response.data.clazzList);
        this.listLoading = false;
      });
    },
    //去添加
    toSave() {
      this.$refs.edit3.open({}, "添加");
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id
      };
      this.$refs.edit3.open(editParameter, "编辑");
    },
    updateReadst(index, row) {
      let parameter = {
        id: row.id
      };
      api.readst2(
        parameter,
        response => {
          this.$utils.msg.success(response.msg);
          this.list();
        },
        response => {
          this.$utils.msg.warning(response.msg);
        }
      );
    },
    //批量已读
    batchUpdateReadst() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      let parameter = {
        ids: this.selected
      };
      api.readst(
        {
          ids: this.selected
        },
        response => {
          this.$utils.msg.success(response.msg);
          this.list();
        },
        response => {
          this.$utils.msg.warning(response.msg);
        }
      );
    },
    //去详情
    toDetail(index, row) {
      let detailParameter = {
        id: row.id
      };
      this.$refs.edit3.open(detailParameter, "详情", true);
    },
    //删除
    remove(index, row) {
      let parameter = {
        ids: row.id
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          parameter,
          response => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          response => {
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
        ids: this.selected
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          {
            ids: this.selected
          },
          response => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          response => {
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
      if (this.state == "未读") {
        this.searchParams.readst = 0;
      }
      if (this.state == "已读") {
        this.searchParams.readst = 1;
      }
      if (this.state == "全部") {
        this.searchParams.readst = undefined;
      }
      this.list();
    },
    //清空搜索框
    clearSearchVal() {
      this.searchParams = {};
      this.list();
    }
  },
  mounted() {
    this.list();
    this.listCname();
  }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>

