<template>
  <div></div>
</template>

<script>
export default {
  created() {
    this.$caches.token.set(this.$route.query.token)
    this.$router.push("/")
  },
};
</script>
<style>
</style>