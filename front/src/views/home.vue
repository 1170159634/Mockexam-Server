<template>
  <div class="dashboard-container">
    <img src="../assets/images/background.jpg" style="width: 110%;height:100%;display: inline-block;">
  </div>
</template>

<script>
const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}
import {
  userBehaviorLastLoginTime,
  updateLoginTime,
  userBehaviorConfirmRead,
  userBehaviorJudgeIsRead
} from '@/api/system/question'

export default {
  name: 'Dashboard',
  components: {},
  data() {
    return {
      lineChartData: lineChartData.newVisitis
    }
  },
  created() {
    this.showUserBehavior()
  },
  methods: {
    showUserBehavior() {
      // eslint-disable-next-line eqeqeq
      if (this.$root.firstShowUser == false) {
        userBehaviorLastLoginTime().then((res) => {
          const leaveTime = res.content
          userBehaviorJudgeIsRead().then((res) => {
            const isRead = res.content

            console.log('用户是否已读', isRead)
            updateLoginTime().then((res) => {
              console.log('已更新用户登录时间')
            })
            // eslint-disable-next-line eqeqeq
            if (leaveTime > 3 && isRead == 0) {
              this.$confirm('您已经' + leaveTime + '天未登录该系统了,已为您精心挑选了50道题，是否现在立刻去做？', '欢迎回来', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.$root.firstShowUser = true
                userBehaviorConfirmRead().then((res) => {
                  console.log('用户确认已读')
                })
                this.$router.push({ path: '/question/favorites' })
              })
            }
          })
        })
      }
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
