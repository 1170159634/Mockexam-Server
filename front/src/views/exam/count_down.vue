<template>
  <span>{{ time }}</span>
</template>
<script>
export default {
  props: {
    endTime: {
      type: String
    }
  },
  data() {
    return {
      time: '',
      flag: false,
      interval: null
    }
  },
  mounted() {

  },
  methods: {
    againInit(val) {
      this.endTime = val
      this.interval = setInterval(() => {
        this.timeDown()
      }, 500)
    },
    clear() {
      this.time = null
      clearInterval(this.interval)
    },
    timeDown() {
      const endTime = new Date(this.endTime)
      const nowTime = new Date()
      const leftTime = parseInt((endTime.getTime() - nowTime.getTime()) / 1000)
      const d = parseInt(leftTime / (24 * 60 * 60))
      const h = this.formate(parseInt((leftTime / (60 * 60)) % 24))
      const m = this.formate(parseInt((leftTime / 60) % 60))
      const s = this.formate(parseInt(leftTime % 60))
      if (leftTime <= 0) {
        this.$emit('time-end')
      }
      this.time = `${h}:${m}:${s}` // 需要修改时间样式的话 ，比如需要什么30分钟倒计时，就只保留分和秒
    },
    formate(time) {
      if (time >= 10) {
        return time
      } else {
        return `0${time}`
      }
    }
  }
}
</script>

<style scoped>
</style>
