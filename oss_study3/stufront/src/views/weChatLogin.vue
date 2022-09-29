<template>
  <div class="background">
    <div class="findPwd-frame">
      <el-card class="box-card">
        <!-- <div slot="header" class="clearfix">
          <span id="logo" style="color: #000 !important;">微信登录</span>
        </div> -->
        <div class="text item">
          <wxlogin
            :self_redirect="self_redirect"
            :id="id"
            :appid="appid"
            :scope="scope"
            :redirect_uri="redirect_uri"
            :state="state"
            :style="style"
            :href="href"
          ></wxlogin>
          <el-link type="primary" @click="toLogin">返回登录</el-link>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import api from "@/api/index.js";
import wxlogin from "vue-wxlogin";
export default {
  name: "weChatLogin",
  components: {
    wxlogin,
  },
  data() {
    return {
      self_redirect: "false",
      id: "login_container",
      appid: "",
      scope: "snsapi_login",
      redirect_uri: "",
      state: "",
      style: "",
      href: "",
    };
  },
  methods: {
    toLogin() {
      this.$router.replace("/login");
    },
    init(){
      api.getWechatLoginInfo(null,(res)=>{
          let {appId,redirectUri} = res.data
          this.appid = appId
          this.redirect_uri = encodeURIComponent(redirectUri)
      });
    }
  },
  created(){
   this.init();
  }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/findPwd.scss";
</style>
