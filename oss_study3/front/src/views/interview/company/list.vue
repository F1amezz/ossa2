<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="企业名称">
              <el-input v-model="searchParams.name"></el-input>
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
            <el-button @click="clearSearchVal">清空1</el-button>
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
    </div>
    <div class="operator">
      <el-button type="primary" icon="el-icon-plus" @click="toSave()"
        >添加</el-button
      >
      <el-button type="danger" icon="el-icon-delete" @click="batchRemove()"
        >批量删除</el-button
      >
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
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
        ></el-table-column>
        <el-table-column prop="name" width="210" label="企业名称">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="tel" width="130" label="电话"></el-table-column>
        <el-table-column
          prop="maxemps"
          width="90"
          label="规模"
        ></el-table-column>
        <el-table-column prop="source" width="90" label="来源类型">
          <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.source == 1">学生</el-tag>
            <el-tag type="danger" v-if="scope.row.source == 2" size="mini"
              >内部</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="mobile"
          width="180"
          label="联系人电话"
        ></el-table-column>
        <el-table-column prop="url" width="110" label="网站"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" fixed="right" width="320px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button size="mini" @click="toDetail(scope.$index, scope.row)"
                >详情</el-button
              >
              <el-button size="mini" @click="toAddPost(scope.row)"
                >岗位</el-button
              >
              <el-button size="mini" @click="toAddMians(scope.row)"
                >面试</el-button
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
    <addpost ref="addpost"></addpost>

    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="面试学生" :label-width="formLabelWidth">
          <el-autocomplete
            v-model="sname"
            class="inline-input"
            :fetch-suggestions="queryStudents"
            placeholder="请输入内容"
            :trigger-on-focus="false"
            @select="handleSelecStu"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="岗位" :label-width="formLabelWidth">
          <el-select v-model="form.postid" placeholder="请选择岗位">
            <el-option
              v-for="item in postsList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="面试时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.mstm"
            type="datetime"
            placeholder="选择面试日期"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sbmtMianshi"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import edit from "./edit.vue";
import addpost from "../posts/edit.vue";

import api from "@/api/interview/company/company.js";
import stuApi from "@/api/stus/student/student.js";
import postsApi from "@/api/interview/posts/posts.js";
import mianshiApi from "@/api/interview/mianshi/mianshi.js";

export default {
  name: "company",
  components: {
    edit,
    addpost,
  },
  data() {
    return {
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      form: {},
      sname: "",
      page: new this.$constant.pageObj(),
      dialogFormVisible: false,
      formLabelWidth: "120px",
      dialogTitle: "",
      postsList: [],
      company:{}
    };
  },
  methods: {
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        this.listLoading = false;
      });
    },sbmtMianshi(){

       let params = this.$utils.merger(this.form, {cmpname:this.company.name});
       
       mianshiApi.save(params,res=>{
            
            if(res.code == 1000){
                 this.dialogFormVisible = false  
                 this.$utils.msg.success(res.msg);
                 this.form = {}
                 this.sname =""
            }else{
                 this.$utils.msg.warning(res.msg);
            }
            //   console.log(res)

       });

    },
    queryStudents(queryString, cb) {
      stuApi.intnStu({ name: queryString }, (res) => {
        let stus = res.data;
        console.log(stus);
        let results = queryString
          ? stus.filter(this.studentsFilter(queryString))
          : stus;
          console.log(results);
        // 调用 callback 返回建议列表的数据
        cb(results);
      });
    },
    studentsFilter(queryString) {
      return (stu) => {
        return stu.value.indexOf(queryString) === 0;
      };
    },
    handleSelecStu(item) {
      console.log(item);
      this.form.stuid = item.id;
    },

    toAddMians(cmp) {
      //面试登记
      this.dialogFormVisible = true;
      this.dialogTitle = "添加 " + cmp.name + " - 面试记录";
      this.company = cmp

      postsApi.listByCid({ cmpid: cmp.id }, (res) => {
        this.postsList = res.data;
      });
    }, //去添加岗位
    toAddPost(cmp) {
      this.$refs.addpost.open({}, cmp.name + "-添加岗位", cmp.id);
    },
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加-就业企业");
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

