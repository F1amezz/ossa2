<template>
  <div></div>
</template>

<script>
export default {
  created() {
	  debugger
    this.$utils.msg.warning(this.$route.query.msg);
    this.$router.push("/weChatLogin")
  },
};
</script>
<style>
</style>