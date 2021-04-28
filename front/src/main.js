import Vue from 'vue'

import Cookies from 'js-cookie'
import 'normalize.css/normalize.css'
import Element from 'element-ui'
//
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

// 数据字典
import dict from './components/Dict'
// 权限指令
import checkPer from '@/utils/permission'
import permission from './components/Permission'
import './assets/styles/element-variables.scss'
// global css
import './assets/styles/index.scss'

// 代码高亮
import VueHighlightJS from 'vue-highlightjs'
import 'highlight.js/styles/atom-one-dark.css'

import App from './App'
import store from './store'
import router from './router/routers'

import './assets/icons' // icon
import './router/index' // permission control
import 'echarts-gl'
Vue.prototype.firstShowUser = false
// 表格展示科目类型转换
Vue.prototype.subjectTypeFormat = function(row, column) {
  if (row.subjectType === 1) {
    return '科目一'
  } else if (row.subjectType === 4) {
    return '科目四'
  }
}
// 表格展示试题类型转换
Vue.prototype.questionTypeFormat = function(row, column) {
  if (row.questionType === 1) {
    return 'C1'
  } else if (row.questionType === 2) {
    return 'C2'
  } else if (row.questionType === 3) {
    return 'B2'
  } else if (row.questionType === 4) {
    return 'A1'
  }
}
// 服务器ip
Vue.prototype.serverIp = 'http://127.0.0.1:8000/'
// 本地ip
Vue.prototype.localIp = 'http://127.0.0.1:8000/'
// 表格展示选项转换
Vue.prototype.answerFormat = function(row, column) {
  if (row.answer === '1') {
    return '选项1'
  } else if (row.answer === '2') {
    return '选项2'
  } else if (row.answer === '3') {
    return '选项3'
  } else if (row.answer === '4') {
    return '选项4'
  } else if (row.answer === '7') {
    return '选项1,2'
  } else if (row.answer === '8') {
    return '选项1,3'
  } else if (row.answer === '9') {
    return '选项1,4'
  } else if (row.answer === '10') {
    return '选项2,3'
  } else if (row.answer === '11') {
    return '选项2,4'
  } else if (row.answer === '12') {
    return '选项3,4'
  } else if (row.answer === '13') {
    return '选项1,2,3'
  } else if (row.answer === '14') {
    return '选项1,2,4'
  } else if (row.answer === '15') {
    return '选项1,3,4'
  } else if (row.answer === '16') {
    return '选项2,3,4'
  } else if (row.answer === '17') {
    return '选项1,2,3,4'
  }
}

Vue.use(checkPer)
Vue.use(VueHighlightJS)
Vue.use(mavonEditor)
Vue.use(permission)
Vue.use(dict)
Vue.use(Element, {
  size: Cookies.get('size') || 'small' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
