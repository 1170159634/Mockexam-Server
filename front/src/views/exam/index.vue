<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--题库数据-->
      <el-col :xs="15" :sm="18" :md="19" :lg="20" :xl="20">
        <!--工具栏-->
        <div class="head-container">
          <div v-if="crud.props.searchToggle">
            <el-select
              v-model="query.carType"
              clearable
              size="small"
              placeholder="车类型"
              class="filter-item"
              style="width: 90px"
            >
              <el-option
                v-for="item in carTypeOtions"
                :key="item.key"
                :label="item.display_name"
                :value="item.key"
              />
            </el-select>
            <el-select
              v-model="query.subjectType"
              clearable
              size="small"
              placeholder="科目"
              class="filter-item"
              style="width: 90px"
            >
              <el-option
                v-for="item in subjectTypeOtions"
                :key="item.key"
                :label="item.display_name"
                :value="item.key"
              />
            </el-select>
            <!-- 搜索    一旦有传参进去  直接 写 query.[参数名] 就好 -->
            <!-- 搜索 -->
            <rrOperation />

          </div>
          <crudOperation show="" :permission="permission" />

        </div>

        <div style="margin-left: 30px" align="center">
          <countDownTime ref="child" :end-time="endTime" style="font-size:24px" @time-end="timeOut" />
          <el-alert
            v-if=" errorShow"
            center
            style="width: 180px"
            title="选择错误"
            type="error"
            show-icon
            close
          />
          <el-alert
            v-if=" successShow"
            center
            style="width: 180px"
            title="选择正确"
            type="success"
            show-icon
            close
          />
          <br>
          <h2>{{ index }}/{{ questionCount }} {{ questionForm.question }} </h2>  <br>
          <img v-if="questionForm.targetPic" style="width: 180px;height: 180px; " :src="this.serverIp+questionForm.targetPic">
          <br>

          <el-radio-group
            v-model="questionForm.chooseAnswer"
            size="big"
            fill="#d4237a"
            :disabled=" questionForm.isChooseOver"
            @change="changeOption"
          >

            <el-radio label="1" border>A:{{ questionForm.option1 }}</el-radio>
            <br><br><br>
            <el-radio label="2" border>B:{{ questionForm.option2 }}</el-radio><br><br><br>
            <el-radio v-if="questionForm.option3" label="3" border>C:{{ questionForm.option3 }}</el-radio><br><br><br>
            <el-radio v-if="questionForm.option4" label="4" border>D:{{ questionForm.option4 }}</el-radio><br><br><br>
          </el-radio-group><br> <br>
          <el-tag v-if="questionForm.isChooseOver" size="medium">
            {{ questionForm. questionExplain }}
          </el-tag><br> <br>
          <el-button type="primary" @click="lastQuestion">上一题</el-button>
          <el-button type="primary" @click="nextQuestion">下一题</el-button><br><br>
          <el-button type="danger" @click="submitExam">提交试卷</el-button><br><br>
          <el-link v-if="questionForm.errorShow" type="danger"> 正确答案:
            {{ questionForm.answer=='4'?('D'):( questionForm.answer=='3'?('C'):( questionForm.answer=='2'?('B'):(questionForm.answer=='1'?('A'):'')) ) }}</el-link><br><br>
          <el-link type="success">答对:{{ successCount }}道</el-link>
          <el-link type="danger"> 答错:{{ wrongCount }}道</el-link>
          <el-link type="info">正确率:{{ successPer }}</el-link>
        </div>

      </el-col>
    </el-row>
  </div></template>

<script>
import crudQuestion from '@/api/system/question'
import CRUD, { crud, form, header, presenter } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
import { questionSelectExamQuestion, questionSelectById, questionChooseAnswer } from '@/api/system/question'
import countDownTime from './count_down'
import moment from 'moment'
// 表单数
const defaultForm = {
  userId: null,
  id: null,
  option1: null,
  option2: null,
  option3: null,
  option4: null,
  answer: null,
  targetPic: null,
  questionType: null,
  subjectType: null,
  questionExplain: null,
  question: null

}
export default {
  name: 'QuestionPage',
  components: { crudOperation, rrOperation, countDownTime },

  // 在这里添加方法
  cruds() {
    // this.query.userId = this.user.id
    return CRUD({ title: '编辑', url: 'api/mockexam/question/selectExamQuestion', crudMethod: { ...crudQuestion }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  // 数据字典
  dicts: ['user_status'],
  data() {
    return {
      endTime: '2021-04-18 23:59:00',
      countTime: {
        minute: 11,
        second: 11
      },
      firstShow: false,
      // 选择的结果总数
      chooseCount: 0,
      // 错误的结果总数
      wrongCount: 0,
      // 成功的结果总数
      successCount: 0,
      // 正确率
      successPer: null,
      isChooseOver: false,
      // 成功或失败组件展示
      successShow: false,
      errorShow: false,
      // 单选题选择的答案
      chooseAnswer: null,
      // 返回的试题总数
      questionCount: 0,
      // 当前数组index
      questionIndex: 0,
      // selectById的问题包装类
      questionForm: {
        id: null,
        question: null,
        option1: null,
        option2: null,
        option3: null,
        option4: null,
        answer: null,
        questionExplain: null,
        targetPic: null,
        chooseAnswer: null,
        isChooseOver: false,
        successShow: false,
        errorShow: false,
        questionResult: {
          explain: null,
          isError: null,
          result: null
        }
      },
      // 一旦selectById，添加到里面
      questionList: [],
      // selectIds包装的类
      questionIndexList: [],
      index: 0,
      id: 0,
      subjectTypeOtions: [
        { key: 1, display_name: '科目一' },
        { key: 4, display_name: '科目四' }
      ],
      carTypeOtions: [
        { key: 1, display_name: '小车' },
        { key: 2, display_name: '货车' },
        { key: 3, display_name: '客车' }
      ],
      headers: {
        'Authorization': getToken()
      },
      // 展示列id
      showRowId: false,
      // 上传标识
      picshow: false,
      height: document.documentElement.clientHeight - 180 + 'px;',
      permission: {
        add: ['admin1', 'user:add'],
        edit: ['admin1', 'user:edit'],
        del: ['admi1n', 'user:del']
      }

    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  watch: {
    chooseCount(newVal, oldVal) {
      var per = this.successCount / newVal
      this.successPer = Number(per * 100).toFixed(2) + '%'
    },
    wrongCount(newVal, oldVal) {
      if (this.firstShow === false) {
        return
      }
      if (newVal > 10) {
        this.$confirm('您已经答错了' + newVal + '道题，成绩不合格，是否交卷？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log('changge2', this.successCount + '道题,' + '答错了' + this.wrongCount)
          this.$alert('您答对了' + this.successCount + '道题,' + '答错了' + this.wrongCount + '道题,' + '正确率:' + this.successPer, '成绩报告', {
            confirmButtonText: '确定',
            callback: action => {
              this.$refs.child.clear()
            }
          })
        })
        this.firstShow === false
      }
    },
    // 按照科目类型搜索:
    query: {
      handler(newVal, oldVal) {
        console.log('已执行')
        this.initExamData()
      }, deep: true
    }},

  created() {
    var params = { subjectType: this.query.subjectType, carType: this.query.carType, isRandom: 1 }

    questionSelectExamQuestion(params).then((res) => {
      this.questionIndexList = res.content
      this.questionCount = res.totalElements
      this.selectQuetsionById(this.questionIndexList[this.index])
      moment(new Date()).add(1, 'minutes').calendar()
    })
    this.firstShow = true
  },
  mounted: function() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 180 + 'px;'
    }
  },
  methods: {
    initExamData() {
      this.questionForm = {}
      this.questionList = []
      this.index = 0
      this.chooseCount = 0
      this.wrongCount = 0
      this.successCount = 0
      this.successPer = null
      var params = { subjectType: this.query.subjectType, carType: this.query.carType, isRandom: 1 }
      this.$refs.child.clear()
      // eslint-disable-next-line eqeqeq
      if (typeof (this.query.subjectType) === 'undefined' || typeof (this.query.carType) === 'undefined' || this.query.subjectType == '' || this.query.carType == '') {
        return
      }
      questionSelectExamQuestion(params).then((res) => {
        this.questionIndexList = res.content
        this.questionCount = res.totalElements
        this.selectQuetsionById(this.questionIndexList[this.index])
        this.endTime = moment(new Date()).add(1, 'minutes').format('YYYY-MM-DD HH:mm:ss')
        // eslint-disable-next-line eqeqeq
        this.$refs.child.againInit(this.endTime)
      })
    },
    submitExam() {
      this.$confirm('确定要提交试卷吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.child.clear()
        this.$alert('您答对了' + this.successCount + '道题,' + '答错了' + this.wrongCount + '道题,' + '正确率:' + this.successPer, '成绩报告', {
          confirmButtonText: '确定',
          callback: action => {
          }
        })
      })
    },
    timeOut() {
      if (this.firstShow === false) {
        return
      }
      this.$refs.child.clear()
      this.firstShow === false
      this.$alert('您答对了' + this.successCount + '道题,' + '答错了' + this.wrongCount + '道题,' + '正确率:' + this.successPer, '成绩', {
        confirmButtonText: '确定',
        callback: action => {
        }
      })
    },
    nextQuestion() {
      var id = this.questionIndexList[ this.index]
      var data = this.questionList[this.index ]
      if (data === undefined) {
        this.selectQuetsionById(id)
      } else {
        this.questionForm = data
        this.isChooseOver = data.isChooseOver
        this.successShow = data.successShow
        this.errorShow = data.errorShow
        this.index = this.index + 1
      }
    },
    lastQuestion() {
      this.index = this.index - 1
      this.isChooseOver = this.questionList[this.index - 1].isChooseOver
      this.successShow = this.questionList[this.index - 1 ].successShow
      this.errorShow = this.questionList[this.index - 1 ].errorShow
      this.questionForm = this.questionList[this.index - 1]
    },
    changeOption() {
      var index = this.index - 1
      this.questionList[index].isChooseOver = true
      var params = { id: this.questionIndexList[index], answer: this.questionList[index].chooseAnswer }
      questionChooseAnswer(params).then((res) => {
        this.questionList[index].questionResult = res.content
        if (this.questionList[index].questionResult.isError === 1) {
          this.errorShow = true
          this.successShow = false
          this.wrongCount = this.wrongCount + 1
        } else if (this.questionList[index].questionResult.isError === 0) {
          this.errorShow = false
          this.successShow = true
          this.successCount = this.successCount + 1
        }
        this.questionList[index].errorShow = this.errorShow
        this.questionList[index].successShow = this.successShow
        this.chooseCount = this.chooseCount + 1
      })
    },
    selectQuetsionById(index) {
      var data = { id: index }
      questionSelectById(data).then((res) => {
        this.questionForm = res.content
        this.questionForm.isChooseOver = false
        this.questionForm.successShow = false
        this.questionForm.errorShow = false
        this.questionForm.chooseAnswer = null
        this.questionForm.questionResult = null
        this.questionList.push(this.questionForm)
        this.index = this.index + 1

        this.isChooseOver = false
        this.successShow = false
        this.errorShow = false
      })
    },

    // 如果新增，则设置form为空
    [CRUD.HOOK.afterToCU](crud, form) {
      if (crud.status.add === 1) {
        this.form = {}
      } else if (crud.status.edit === 1) {
        this.form = form
      }
    },
    // 新增前将多选的值设置为空
    [CRUD.HOOK.beforeToAdd]() {

    },
    // 初始化编辑时候的角色与岗位
    [CRUD.HOOK.beforeToEdit](crud, form) {

    },
    // 提交前做的操作
    // 非常重要!!!!
    [CRUD.HOOK.afterValidateCU](crud) {
      crud.form = this.form
    }

  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    ::v-deep .vue-treeselect__control, ::v-deep .vue-treeselect__placeholder, ::v-deep .vue-treeselect__single-value {
        height: 30px;
        line-height: 30px;
    }
    .avatar {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }
</style>
