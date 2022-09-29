<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="岗位名称" prop="name"
              ><el-input
                v-model="formParameter.name"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
		   <el-col :span="16">
            <el-form-item label="开始时间" prop="bgntm">
			        <el-date-picker
              v-model="value1"
              type="daterange"
			  value-format="yyyy-MM-dd"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
			  </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="招聘人数" prop="maxneed"
              ><el-input
                v-model="formParameter.maxneed"
                autocomplete="off"
                placeholder="单位个"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="学历" prop="xueli"
              >
               <select-option-dictionary v-model="formParameter.xueli" dkey="xueli"></select-option-dictionary>
            </el-form-item>
          </el-col>
		 <el-col :span="8">
            <el-form-item label="工作经验" prop="exps"
              ><el-input
                v-model="formParameter.exps"
                autocomplete="off"
                placeholder="例如:1年"
              ></el-input
            ></el-form-item>
          </el-col>
         
		 </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="待遇" prop="sarry"
              ><el-input
                v-model="formParameter.sarry"
                autocomplete="off"
                placeholder="例如:6.5k-8k"
              ></el-input
            ></el-form-item>
          </el-col>
  
          <el-col :span="16">
            <el-form-item label="技术要求" prop="tech"
              ><el-input
                v-model="formParameter.tech"
                autocomplete="off"
                 placeholder="精通echarts、前端666"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark"
              ><el-input
                type="textarea"
                v-model="formParameter.remark"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button
        v-if="!disabled"
        type="primary"
        :loading="confirmLoading"
        @click="confirm()"
        >确 定</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>

import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/interview/posts/posts.js";
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';


export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary
  },
  data() {
    return {
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      cmpId:'',
	    value1:[],
      rules: {
        //name
        name: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          }
        ],
        //经验
        exps: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          }
        ],
        //学历
        xueli: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          }
        ],
      },
    };
  },
  methods: {
    //打开弹框
    open(parameter, title, cmpId,disabled, size) {
      this.parameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, size);
     if(cmpId)
        this.cmpId = cmpId

      this.$nextTick(() => {
        this.init();
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },
    //提交表单
    confirm() {
      this.$utils.checkForm(this.$refs.form, () => {
      this.confirmLoading = true;
		  if(this.value1 && this.value1.length==2){
          this.formParameter.bgntm = this.value1[0]
		      this.formParameter.endtm = this.value1[1]
	   	}
		
        if (!this.parameter.id) {
          //添加
          let parameter = this.$utils.merger(this.formParameter);
          parameter.company = this.cmpId
		      console.log(parameter)
	        api.save(
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

        } else {
          //编辑
          let parameter = this.$utils.merger(this.formParameter);
          api.update(
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
        }
      });
    },
    init() {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;
      //清空内容
      this.formParameter = {};
      //清楚校验
      this.$refs.form.clearValidate();

      if (this.parameter.id) {
        //编辑查询数据
        let parameter = {
          id: this.parameter.id,
        };
        api.toUpdate(parameter, (response) => {
          this.formParameter = response.data;
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
