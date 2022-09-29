<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="姓名">
              <el-autocomplete
                v-model="searchParams.name"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入内容"
                @select="handleSelect"
              ></el-autocomplete>
              <!-- <el-input v-model="searchParams.name"></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="状态">
              <el-select v-model="state" @change="search" placeholder="请选择">
                <el-option v-for="(i) in ['在读','休学','毕业','全部']" :key="i" :label="i" :value="i"></el-option>
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
    <div class="operator">
      <el-button type="primary" icon="el-icon-plus" @click="toSave()"
        >添加</el-button
      >
      <el-button type="primary" icon="el-icon-plus" @click="toImport()"
        >导入</el-button >
      <el-button type="primary" icon="el-icon-plus" @click="toExport()"
        >下载导入模板</el-button >
      <el-button type="primary" @click="outports()">导出</el-button>
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
        @selection-change="selectionChange"
        height="auto"
        v-loading="listLoading"
      >
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
        ></el-table-column>
        <el-table-column prop="name" label="学生&班级">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.name }}</el-tag>
            <br />
            <el-tag size="mini" type="warning">{{
              scope.row.dbColumn_cid
            }}</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="dbColumn_cid" label="班级名称"></el-table-column> -->
        <el-table-column prop="head" label="头像">
          <template slot-scope="scope">
            <el-avatar
              size="large"
              :src="scope.row.head"
              v-if="scope.row.head"
            ></el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="sex" width="60" label="性别">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.sex == 1" size="mini">男</el-tag>
            <el-tag v-if="scope.row.sex == 2" size="mini" type="danger"
              >女</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="mobile" label="联系电话"></el-table-column>
        <el-table-column prop="groupleader" width="60" label="职务">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.groupleader == 2" size="mini">组长</el-tag>
            <el-tag v-if="scope.row.groupleader == 1" size="mini" type="warning"
              >班长</el-tag
            >
            <el-tag v-if="scope.row.groupleader == 0" size="mini" type="success"
              >组员</el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="school" label="学校&专业">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.school }}</el-tag>
            <br />
            <el-tag size="mini" type="success">{{
              scope.row.professional
            }}</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="professional" label="专业"></el-table-column> -->
        <el-table-column prop="nativeplace" label="籍贯"></el-table-column>
        <el-table-column
          prop="cometime"
          width="100"
          label="报到时间"
        ></el-table-column>
        <!-- <el-table-column prop="relationship" label="紧急联系人关系:">
					<template slot-scope="scope">
						<div v-if="scope.row.state == 1">父亲</div>
						<span v-if="scope.row.state == 2">母亲</span>
						<span v-if="scope.row.state == 3">兄弟</span>
						<span v-if="scope.row.state == 4">姐妹</span>
						<span v-if="scope.row.state == 5">亲戚</span>
						<span v-if="scope.row.state == 6">朋友</span>
					</template>
				</el-table-column>
				<el-table-column prop="rname" label="紧急联系人名字"></el-table-column>
				<el-table-column prop="rphone" label="紧急联系人电话"></el-table-column> -->
        <!-- <el-table-column prop="schooltime" label="上课时间"></el-table-column> -->
        <!-- <el-table-column prop="describes" label="描述"></el-table-column> -->
        <el-table-column prop="state" width="80" label="状态">
          <template slot-scope="scope">
            <div v-if="scope.row.state == 1">在读</div>
            <span v-if="scope.row.state == 2">休学</span>
            <span v-if="scope.row.state == 3">毕业</span>
            <span v-if="scope.row.state == 4">待报到</span>
          </template>
        </el-table-column>
        <el-table-column prop="historyclass" width="80" label="转班记录">
          <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.historyclass == undefined"
              >无</el-tag
            >
            <el-tag
              size="mini"
              v-else
              @click="showHistoryClass(scope.row.historyclass)"
              >查看</el-tag
            >
          </template>
        </el-table-column>
        <!-- <el-table-column prop="sex" label="性别">
          <template slot-scope="scope">
            <div v-if="scope.row.sex == 1">男</div>
            <span v-if="scope.row.sex == 2">女</span>
          </template>
        </el-table-column> -->
        <!-- <el-table-column prop="mobile" label="联系电话"></el-table-column> -->
        <!-- <el-table-column prop="lname" label="登录名"></el-table-column> -->
        <!-- <el-table-column prop="remarks" label="备注"></el-table-column> -->
        <!-- <el-table-column prop="historytime" label="转班时间"></el-table-column>
        <el-table-column prop="adress" label="地址"></el-table-column>
        <el-table-column prop="wxappuid" label="微信小程序unionID"></el-table-column>
				<el-table-column prop="wxappopenid" label="微信小程序openID"></el-table-column>
				<el-table-column prop="pwd" label="学生密码"></el-table-column>
        <el-table-column label="生日" @click="birthday"></el-table-column>
        <el-table-column prop="email" label="email"></el-table-column>
        <el-table-column prop="isdisable" label="是否禁用"></el-table-column>
        <el-table-column prop="crtm" label="创建时间"></el-table-column>
				<el-table-column prop="mdtm" label="修改时间"></el-table-column>
				<el-table-column prop="crer" label="创建人"></el-table-column>
				<el-table-column prop="mder" label="修改人"></el-table-column> -->
        <el-table-column label="操作" fixed="right" width="300px">
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
              <el-button size="mini" @click="revise(scope.$index, scope.row)"
                >重置密码</el-button
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
    <div>
      <el-dialog
        title="转班纪录查看"
        :visible.sync="dialogVisible2"
        width="22%"
      >
        <span :key="index" v-for="(tag, index) in urlList"
          >转班时间：{{ tag.time }}<br />
          从<el-tag>{{ tag.name1 }}</el-tag
          >转到<el-tag>{{ tag.name }}</el-tag
          ><br />
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible2 = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
    <el-dialog title="上传学生信息" :visible.sync="dialogVisible" width="30%">
      <excel-upload
        drag
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
    <show @flush="list()" ref="show"></show>
  </div>
</template>
<script>
import show from "./showUpdata.vue";
import edit from "./edit.vue";
import api from "@/api/stus/student/student.js";
import excelUpload from "@/components/biz/fileUpload/excelUpload.vue";
export default {
  name: "student",
  components: {
    edit,
    excelUpload,
    show,
  },
  data() {
    return {
      state: "在读",
      searchParams: {},
      urlList: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
      upsize: "512",
      dialogVisible: false,
      dialogVisible2: false,
    };
  },
  methods: {
    querySearchAsync(queryString, cb) {
      if (queryString && queryString.length > 0) {
        api.getStulist(
          { name: queryString },
          (res) => {
             let stus = res.data;
        let results = queryString
          ? stus.filter(this.studentsFilter(queryString))
          : stus;
              cb(results);
   
          },
        );
      }
    },
    studentsFilter(queryString) {
      return (stu) => {
        stu.value = stu.name;
        return stu.value.indexOf(queryString) === 0;
      };
    },
    handleSelect(item) {
      console.log('item: ', item);
      this.searchParams.name = item.name;
    },
    toExport() {
      var a = document.createElement("a"); //创建一个<a></a>标签
      console.log('a: ', a);
      a.href = "/static/students.xlsx"; // 给a标签的href属性值加上地址
      a.download = "学生信息导入模板.xlsx"; //设置下载文件文件名，这里加上.xlsx指定文件类型
      console.log('a: ', a);
      a.style.display = "none"; // 障眼法藏起来a标签
      document.body.appendChild(a); // 将a标签追加到文档对象中
      console.log('a: ', a);

      a.click(); // 模拟点击了a标签，会触发a标签的href的读取
      a.remove(); // 一次性的，用完就删除a标签
    },
    showHistoryClass(t) {
      this.urlList = JSON.parse(t);
      this.dialogVisible2 = true;
    },
    //去导入
    toImport: function () {
      this.dialogVisible = true;
    },

    uploadSuccess(res) {
      // console.log(res);
      if (res && res.length > 32) {
        this.$utils.msg.success("上传成功");
        this.dialogVisible = false;
        let fnm = res.substr(res.lastIndexOf("/") + 1);

        api.toImport({ fnm: fnm }, (res2) => {
          console.log('res2.data: ', res2.data);
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
    },
    //重置密码
    revise(index, row) {
      let parameter = row.id;
      this.$utils.confirm.warning("提示", "确定重置密码吗？", () => {
        api.revise(
          {
            id: parameter,
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
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加");
      this.$refs.edit.mo();
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
      };
      let cid = row.cid;
      this.$refs.edit.open(editParameter, "编辑", false, cid);
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
    //批量导出
    outports() {
      var url = "http://127.0.0.1:8036/study3/stus/student/exportScore";
      axios({
        method: "get",
        url: url,
        params: this.tableFrom,
        responseType: "blob",
      })
        .then((response) => {
          const content = response.data;
          const blob = new Blob([content], {
            type: "application/vnd.ms-excel",
          });
          const fileName = "学生信息.xls";
          if ("download" in document.createElement("a")) {
            // 非IE下载
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink);
          } else {
            // IE10+下载
            navigator.msSaveBlob(blob, fileName);
          }
        })
        .catch((err) => {
          console.log(err);
        });
      // let fname = "coremodel" + new Date().getTime() + ".xls";
      // let parameter = {
      //   // id: this.modelInfo.id,
      //   fn: fname,
      // };
      // api.outports(parameter, fname, "excel");
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
      if (this.state == "在读") {
        this.searchParams.state = 1;
      }
      if (this.state == "休学") {
        this.searchParams.state = 2;
      }
      if (this.state == "毕业") {
        this.searchParams.state = 3;
      }
      if (this.state == "全部") {
        this.searchParams.state= undefined;
      }
      this.list();
      this.listLoading = true;
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

