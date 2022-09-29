<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form class="dataForm" ref="form" :model="formParameter" :inline="true" label-width="80px" :rules="rules">
        <el-row>
          <el-col :span="6">
            <el-form-item label="学生姓名" prop="stuid">
              <el-input :readonly="true" v-model="formParameter.dbColumn_stuid" autocomplete="off"></el-input>
              <el-cascader v-model="formParameter.dbColumn_stuid" :options="options" @change="handleChange"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="企业名称" prop="cmpname">
              <el-input :readonly="true" v-model="formParameter.cmpname" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="岗位" prop="postid">
              <el-input :readonly="true" v-model="formParameter.dbColumn_postid" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试时间" prop="mstm">
              <el-input :readonly="true" v-model="formParameter.mstm" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item label="面试准备" prop="mszbqk">
              <el-rate v-model="formParameter.mszbqk" :colors="colors">
              </el-rate>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试打分" prop="msscore">
              <el-rate v-model="formParameter.msscore" :colors="colors">
              </el-rate>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试结果" prop="msresult">
              <select-option-dictionary v-model="formParameter.msresult" dkey="ms-result"></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试时长" prop="mslast">
              <el-input placeholder="单位分钟" v-model="formParameter.mslast" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="面试总结" prop="mszj">
              <wang-edit v-model="formParameter.mszj"></wang-edit>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="面试难题">
              <el-row :span="12" style="display: flex; flex-wrap: wrap">
                <el-form-item v-for="(item, index) in fileList" :key="index">
                  <image-upload @changex="onChange" v-model="item[Object.keys(item)[0]]" model="fileSystem"
                    uri="/statics/exam" :size="512" />
                </el-form-item>
              </el-row>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/interview/mianshi/mianshi.js";
import wangEdit from "@/components/wangEdit/wangEdit.vue";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
import imageUpload from "@/components/biz/imageUpload/imageUpload.vue";

export default {
  name: "edit",
  components: {
    popup,
    wangEdit,
    selectOptionDictionary,
    imageUpload,
  },
  data() {
    return {
      disabled: true,
      popupLoading: false,
      confirmLoading: false,
      fileList: [{}],
      formParameter: {},
      colors: { 3: "#CC3366", 4: "#00CC33", 5: "#00CC33" },
      rules: {
        //面试准备情况：ABCD
        mszbqk: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //面试过程打分
        msscore: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //面试结果
        msresult: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    //打开弹框
    open(parameter, title, disabled, size) {
      this.formParameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, size);
    },
    onChange() {
      //console.log( this.fileList[this.fileList.length-1]   )
      // console.log( typeof(this.fileList[this.fileList.length-1] )  )
      if (this.fileList[this.fileList.length - 1].undefined) {   //操作最后一个时加一个新的
        this.fileList.push({})
      }

    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
      this.formParameter = {};
    },
    //提交表单
    confirm() {
      this.$utils.checkForm(this.$refs.form, () => {
        this.confirmLoading = true;
        //总结                     对象clone
        let parameter = this.$utils.merger(this.formParameter);


        let exams = [];

        if (this.fileList && this.fileList.length > 0) {
          this.fileList.forEach(v => {
            if (v.undefined) {
              exams.push(v.undefined)
            }
          });
        }
        if (exams.length > 0) {
          parameter.exam = JSON.stringify(exams);
        }

        //删除属性
        delete parameter.cmpname;
        delete parameter.crer;
        delete parameter.crtm;
        delete parameter.dbColumn_postid;
        delete parameter.dbColumn_stuid;
        delete parameter.mder;
        delete parameter.mdtm;
        delete parameter.mstm;
        delete parameter.postid;
        delete parameter.stuid;

        console.log(this.formParameter);

        api.zhjie(
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
      });
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
