<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :model="formParameter"
        :inline="true"
        label-width="80px"
		:disabled="disabled"
        :rules="rules"
      >
        <el-row>
          <el-col :span="6">
            <el-form-item label="学生姓名" prop="stuid"
              ><el-input
                :readonly="true"
                v-model="formParameter.dbColumn_stuid"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="企业名称" prop="cmpname"
              ><el-input
                :readonly="true"
                v-model="formParameter.cmpname"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="岗位" prop="postid"
              ><el-input
                :readonly="true"
                v-model="formParameter.dbColumn_postid"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试时间" prop="mstm"
              ><el-input
                :readonly="true"
                v-model="formParameter.mstm"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="面试准备" prop="mszbqk">
              <el-rate
                :disabled="true"
                v-model="formParameter.mszbqk"
                :colors="colors"
              >
              </el-rate>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试打分" prop="msscore">
              <el-rate
                :disabled="true"
                v-model="formParameter.msscore"
                :colors="colors"
              >
              </el-rate>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试结果" prop="msresult">
              <select-option-dictionary
                :disabled="true"
                v-model="formParameter.msresult"
                dkey="ms-result"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="面试时长" prop="mslast"
              ><el-input
                placeholder="单位分钟"
                :readonly="true"
                v-model="formParameter.mslast"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="面试总结" prop="mszj">
                 <div v-html="formParameter.mszj" class="content-html"></div>
            </el-form-item>
          </el-col>
        </el-row>
		<el-divider></el-divider>
		   <el-row>
          <el-col :span="24">
            <el-form-item label="面试点评" prop="msdp">
                 	<wang-edit v-if="!disabled"   v-model="formParameter.msdp" ></wang-edit>
					   <div v-if="disabled"  v-html="formParameter.msdp"  class="content-html"></div>
            </el-form-item>
          </el-col>
        </el-row>	
		   <el-row  v-show="urls">
          <el-col :span="24">
            <el-form-item label="面试难题" prop="exam">
                <el-image  v-for="url in urls" :key="url" :src="url" lazy></el-image>
            </el-form-item>
          </el-col>
        </el-row>	
      </el-form>
    </div>
   
    <div slot="footer">
      <el-button type="primary"  v-if="!disabled" :loading="confirmLoading" @click="confirm()"
        >确 定</el-button
      >
      <el-button @click="close">关 闭</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/interview/mianshi/mianshi.js";
import wangEdit from "@/components/wangEdit/wangEdit.vue";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "edit",
  components: {
    popup,
    wangEdit,
    selectOptionDictionary,
  },
  data() {
    return {
      disabled: true,
      popupLoading: false,
      confirmLoading: false,
      height: "180px",
      formParameter: {},
      urls:undefined,
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
      if(disabled && this.formParameter.exam  ){  //详情
          this.urls = JSON.parse( this.formParameter.exam)  
      }
      console.log(this.urls)
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

        api.msdp(
          {id:this.formParameter.id,msdp:this.formParameter.msdp},
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

.content-html {
  border: 1px solid #c9d8db !important;
  padding: 4px;
}
</style>
