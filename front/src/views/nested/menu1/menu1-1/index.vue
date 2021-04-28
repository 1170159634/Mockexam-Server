<template>
  <div class="app-container">
    <el-alert :closable="false" title="order" type="success" />
    <div>
      order
    </div>
  </div>
</template>
<script>
export default {
  name: 'Test',
  data() {
    return {
      input: ''
    }
  }
}
</script>
<style scoped>
  .my-code a{
    color:#009688;
  }
</style>
