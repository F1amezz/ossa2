<template>
  <div>
    <img src="~assets/img/banner5.jpg" class="bgImg" />
    <div class="login-card">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span id="logo">{{ getSystemSetting.systemName }}</span>
        </div>
        <div class="systemLogin">
          <el-form
            class="dataForm"
            ref="form"
            :model="formParameter"
            v-loading="loading"
            :rules="rules"
          >
            <el-form-item prop="name">
              <el-input
                v-model="formParameter.name"
                autocomplete="off"
                placeholder="用户名"
              ></el-input>
            </el-form-item>
            <el-form-item prop="pwd">
              <el-input
                show-password
                v-model="formParameter.pwd"
                autocomplete="off"
                placeholder="密码"
              >
              </el-input>
            </el-form-item>
            <el-form-item class="verifyCode" prop="code">
              <el-input
                class="verifyCode-code"
                v-model="formParameter.code"
                autocomplete="off"
                placeholder="验证码"
              >
              </el-input>
              <img
                class="verifyCode-img"
                :src="verifyCode"
                @click="getVerifyCode"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
            </el-form-item>
            <el-link type="primary" @click="toFindPwd">忘记密码</el-link>
          </el-form>
        </div>
        <div class="otherLogin">
          <img class="otherLogin-icon" src="~assets/img/QQ.png" />
          <img
            class="otherLogin-icon"
            src="~assets/img/wechat.png"
            @click="toWechatLogin"
          />
        </div>
      </el-card>
    </div>
    <div id="loginPageText">
      备案号:
      <span>{{ getSystemSetting.icp }}</span>
    </div>
  </div>
</template>

<script>
import api from "@/api/index.js";
import { mapGetters } from "vuex";
export default {
  name: "login",
  data() {
    return {
      formParameter: {
        name: "推荐扫码登录",
        pwd: "",
        code: "",
      },
      loading: false,
      systemsetting: [],
      verifyCode: "",
      key:
        new Date().getTime() +
        "_" +
        parseInt((Math.random() + 1) * Math.pow(10, 5 - 1)),
      rules: {
        name: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        pwd: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        code: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["getSystemSetting"]),
  },
  methods: {
    login() {
      this.$utils.checkForm(this.$refs.form, () => {
        let params = this.$utils.merger(this.formParameter, {
          key: this.key,
        });
        api.login(
          params,
          (response) => {
            this.loading = false;
            this.$caches.token.set(response.data);
            this.$router.replace("/");
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
            this.$caches.token.remove();
            this.getVerifyCode();
            this.loading = false;
          }
        );
      });
    },
    toFindPwd() {
      this.$router.replace("/findPwd");
    },
    getVerifyCode() {
      let params = {
        key: this.key,
      };
      api.getVerifyCode(
        params,
        (response) => {
          this.loading = false;
          this.verifyCode = "data:image/jpeg;base64," + response.data;
        },
        (response) => {
          this.$utils.msg.warning(response.msg);
          this.loading = false;
        }
      );
    },
    toWechatLogin() {
      this.$router.replace("/weChatLogin");
    },
    init() {
      this.$caches.cleanLogin();
      this.getVerifyCode();
      this.formParameter.code = "1";
    },
  },
  mounted() {
    this.init();
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/login.scss";
</style>
