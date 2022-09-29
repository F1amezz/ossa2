<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="4">
            <el-form-item label="课程名称">
              <!-- <el-input v-model="searchParams.name"></el-input> -->
              <el-autocomplete
                v-model="searchParams.name"
                class="inline-input"
                :fetch-suggestions="queryCourse"
                placeholder="请输入内容"
                :trigger-on-focus="false"
                @select="handleSelect"
              ></el-autocomplete>
            </el-form-item> </el-col
          ><el-col :span="4">
            <el-form-item label="版本号">
              <el-autocomplete
                v-model="searchParams.version"
                class="inline-input"
                :fetch-suggestions="queryCourse2"
                placeholder="请输入内容"
                :trigger-on-focus="false"
                @select="handleSelect2"
              ></el-autocomplete>
            </el-form-item> </el-col
          ><el-col :span="4">
            <el-form-item label="类型">
              <select-option-dictionary
                v-model="searchParams.type"
                dkey="course-type"
              ></select-option-dictionary>
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
      <!-- <el-button type="primary" icon="el-icon-plus" @click="toImport()"
        >导入</el-button
      > -->
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
         <!-- dbColumn_sid -->
        <el-table-column prop="year" label="年份" :width="92"></el-table-column>
        <el-table-column
          prop="name"
          label="课程名称"
          :width="220"
        ></el-table-column>
        <el-table-column
          prop="version"
          label="版本号"
          :width="152"
        ></el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          :width="102"
          :formatter="
            (row, column) => {
              return row.type == 1
                ? '前端'
                : row.type == 2
                ? '后端'
                : row.type == 3
                ? '测试'
                : row.type == 0
                ? '全部'
                : '';
            }
          "
          sortable
        ></el-table-column>

        <el-table-column
          prop="days"
          label="天数"
          :width="65"
        ></el-table-column>
        <el-table-column prop="remk" label="备注"></el-table-column>
        <el-table-column label="操作" fixed="right" :width="300">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button
                size="mini"
                @click="toshowSubject(scope.$index, scope.row)"
                >查看科目</el-button
              >
              <!-- <el-button
                size="mini"
                icon="el-icon-search"
                @click="toUpdateSubject(scope.$index, scope.row)"
                >修改科目</el-button
              > -->
              <el-button
                size="mini"
                @click="toDetail(scope.$index, scope.row)"
                >详情</el-button
              >
              <el-button
                size="mini"
                @click="toUpdate(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
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
    <el-dialog
      title="上传学生信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <excel-upload
        drag
        v-model="excel"
        model="fileSystem"
        uri="/statics/excels"
        :size="upsize"
        v-on:change="uploadSuccess"
      >
      </excel-upload>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <edit ref="edit"></edit>
    <edit2 ref="edit2"></edit2>
    <editsubject ref="editSubject"></editsubject>
  </div>
</template>
<script>
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
import excelUpload from "@/components/biz/fileUpload/excelUpload.vue";
import edit from "./edit.vue";
import edit2 from "./edit2.vue";
import editsubject from "./editSubject.vue";
import api from "@/api/edu/course/course.js";
export default {
  name: "course",
  components: {
    selectOptionDictionary,
    edit,
    edit2,
    editsubject,
    excelUpload,
  },
  data() {
    return {
      isShowMoreSearch: false,
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
      upsize: "512",
      dialogVisible: false,
    };
  },
  methods: {
    excel() {},
    queryCourse(queryString, cb) {
      api.list2({ name: queryString }, (res) => {
        let stus = res.data;
        let results = queryString
          ? stus.filter(this.studentsFilter(queryString))
          : stus;
        // 调用 callback 返回建议列表的数据
        cb(results);
      });
    },
    studentsFilter(queryString) {
      return (stu) => {
        stu.value = stu.name;
        return stu.value.indexOf(queryString) === 0;
      };
    },
    handleSelect(item) {
      this.searchParams.name = item.name;
    },
    queryCourse2(queryString, cb) {
      api.list2({ version: queryString }, (res) => {
        let stus = res.data;
        let results = queryString
          ? stus.filter(this.studentsFilter2(queryString))
          : stus;
        // 调用 callback 返回建议列表的数据
        cb(results);
      });
    },
    studentsFilter2(queryString) {
      return (stu) => {
        stu.value = stu.version;
        return stu.value.indexOf(queryString) === 0;
      };
    },
    handleSelect2(item) {
      this.searchParams.version = item.version;
    },

    toImport: function () {
      this.dialogVisible = true;
    },
    handleClose() {},
    uploadSuccess(res) {
      // console.log(res);
      if (res && res.length > 32) {
        this.$utils.msg.success("上传成功");
        this.dialogVisible = false;

        //http://127.0.0.1:8036/study3/statics/excels/f4429aac-f035-4816-b5ab-3c297e50abe7-学生信息.xlsx
        let fnm = res.substr(res.lastIndexOf("/") + 1);

        api.toImport({ fnm: fnm }, (res2) => {
          // console.log(res2);
          this.$refs.show.open(res2.data, "确认导入数据");
        });
      } else {
        this.$utils.msg.warning("上传fail,亲联系管理员.");
      }
    },
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        this.listLoading = false;
      });
      this.searchParams.type = 0;
    },
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加");
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.edit.open(editParameter, "编辑");
    },
    //去编辑
    toUpdateSubject(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.editSubject.open(editParameter, "科目编辑");
    },
    toshowSubject(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.edit2.open(editParameter, "科目查看", true);
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

