<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--题库数据-->
      <el-col :xs="15" :sm="18" :md="19" :lg="20" :xl="20">
        <!--工具栏-->
        <div class="head-container">
          <div v-if="crud.props.searchToggle">
            <el-input
              v-model="query.likeQuestions"
              clearable
              size="small"
              placeholder="输入试题关键词信息进行搜索"
              style="width: 200px;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
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
            <el-select
              v-model="query.questionType"
              clearable
              size="small"
              placeholder="试题类型"
              class="filter-item"
              style="width: 100px"
            >
              <el-option
                v-for="item in questionTypeOtions"
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

        <!--编辑表格-->
        <el-dialog
          append-to-body
          :close-on-click-modal="false"
          :before-close="crud.cancelCU"
          :visible.sync="crud.status.cu > 0"
          :title="crud.status.title"
          width="500px"
        >
          <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="76px">
            <el-form-item label="图片标识" prop="targetPic">

              <div class="el-upload">
                <img v-if="form.targetPic" :src="this.serverIp+form.targetPic" title="点击上传图片" class="avatar" @click="toggleShow">
                <a v-if="!form.targetPic" @click="toggleShow">点此处上传图片 </a>
                <!--  url 为 post请求 修改图片   header表示携带token访问 crop-upload-success 表示上传成功之后-->
                <myUpload
                  v-model="picshow"
                  :headers="headers"
                  :url="this.localIp+'api/mockexam/question/updateQuestionPic'"
                  @crop-upload-success="cropUploadSuccess"
                />
              </div>
            </el-form-item>
            <br>
            <el-form-item label="问题" prop="question">
              <el-input v-model="form.question" />
            </el-form-item>
            <el-form-item label="选项1" prop="option1">
              <el-input v-model="form.option1" />
            </el-form-item>
            <el-form-item label="选项2" prop="option2">
              <el-input v-model="form.option2" />
            </el-form-item>
            <el-form-item label="选项3" prop="option3">
              <el-input v-model="form.option3" />
            </el-form-item>
            <el-form-item label="选项4" prop="option4">
              <el-input v-model="form.option4" />
            </el-form-item>
            <el-form-item label="描述" prop="questionExplain">
              <el-input v-model="form.questionExplain" />
            </el-form-item>
            <el-form-item label="科目类型">
              <el-radio-group v-model="form.subjectType" style="width: 178px">
                <el-radio label="1">科目1</el-radio>
                <el-radio label="4">科目4</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="试题类型">
              <el-radio-group v-model="form.questionType" style="width: 178px">
                <el-radio label="1">C1</el-radio>
                <el-radio label="2">C2</el-radio>
                <el-radio label="3">B2</el-radio>
                <el-radio label="4">A1</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="正确答案">
              <el-radio-group v-model="form.answer" style="width: 178px">
                <el-radio-button label="1" border>A:{{ form.option1 }}</el-radio-button>
                <el-radio-button label="2" border>B:{{ form.option2 }}</el-radio-button>
                <el-radio-button label="3" border>C:{{ form.option3 }}</el-radio-button>
                <el-radio-button label="4" border>D:{{ form.option4 }}</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="crud.cancelCU">取消</el-button>
            <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
          </div>
        </el-dialog>

        <!--展示表格-->
        <el-table
          ref="table"
          v-loading="crud.loading"
          :data="crud.data"
          style="width: 200%;"
          @selection-change="crud.selectionChangeHandler"
        >
          <!--  如果根据不同情况进行展示，:formatteafterValidateCUr="stateFormat" 然后return即可-->
          <el-table-column :selectable="checkboxT" type="selection" width="55" />
          <el-table-column prop="targetPic" label="图片标识">
            <template slot-scope="scope">
              <img v-if="scope.row.targetPic" :src="'http://127.0.0.1/'+scope.row.targetPic" style="width: 100px;height: 100px">
            </template>
          </el-table-column>
          <el-table-column :show-overflow-tooltip="false" prop="question" label="问题" />
          <el-table-column v-if="this.showRowId" :show-overflow-tooltip="true" rop="id" label="主键id" />
          <el-table-column :show-overflow-tooltip="false" prop="option1" label="选项1" />
          <el-table-column :show-overflow-tooltip="false" prop="option2" label="选项2" />
          <el-table-column :show-overflow-tooltip="false" prop="option3" label="选项3" />
          <el-table-column :show-overflow-tooltip="false" prop="option4" label="选项4" />
          <el-table-column :show-overflow-tooltip="true" prop="questionExplain" label="描述" />
          <el-table-column :show-overflow-tooltip="true" :formatter="this.answerFormat" prop="answer" label="答案" />
          <el-table-column label="收藏状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.isMyFavorites"
                :disabled="false"
                active-color="#409EFF"
                inactive-color="#F56C6C"
                @change="changeEnabled(scope.row, scope.row.isMyFavorites)"
              />
            </template>
          </el-table-column>
          <el-table-column :show-overflow-tooltip="true" :formatter="this.subjectTypeFormat" prop="subjectType" label="科目" />
          <el-table-column :show-overflow-tooltip="true" :formatter="this.questionTypeFormat" prop="questionType" label="试题类型" />
        </el-table>
        <!--分页组件-->
        <pagination />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import myUpload from 'vue-image-crop-upload'
import crudQuestion from '@/api/system/question'
import { favoritesOperation } from '@/api/system/question'

import CRUD, { crud, form, header, presenter } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getToken } from '@/utils/auth'

// 表单数据
const defaultForm = {
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
  components: { crudOperation, rrOperation, pagination, myUpload },

  // 在这里添加方法
  cruds() {
    return CRUD({ title: '编辑', url: 'api/mockexam/question/', crudMethod: { ...crudQuestion }})
  },

  mixins: [presenter(), header(), form(defaultForm), crud()],
  // 数据字典
  dicts: ['user_status'],
  data() {
    return {
      subjectTypeOtions: [
        { key: 1, display_name: '科目一' },
        { key: 4, display_name: '科目四' }
      ],
      questionTypeOtions: [
        { key: 1, display_name: 'C1' },
        { key: 2, display_name: 'C2' },
        { key: 3, display_name: 'B2' },
        { key: 4, display_name: 'A1' }
      ],
      rules: {
        question: [
          { required: true, message: '请输入问题', trigger: 'blur' }
        ],
        option1: [
          { required: true, message: '请至少输入两个选项', trigger: 'blur' }
        ],
        option2: [
          { required: true, message: '请至少输入两个选项', trigger: 'blur' }
        ]

      },
      headers: {
        'Authorization': getToken()
      },
      // 展示列id
      showRowId: false,
      // 上传标识
      picshow: false,
      height: document.documentElement.clientHeight - 180 + 'px;',
      permission: {
        add: ['admin', 'user:add'],
        edit: ['admin', 'user:edit'],
        del: ['admin', 'user:del']
      }

    }
  },
  computed: {

  },
  created() {
    this.crud.msg.add = '新增试题成功!'
  },
  mounted: function() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 180 + 'px;'
    }
  },
  methods: {
    // 修改收藏状态
    changeEnabled(data, val) {
      this.$confirm('确认要收藏该试题吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        data.questionId = data.id
        data.favorites = data.isMyFavorites
        favoritesOperation(data).then(res => {
          this.crud.notify('操作成功！')
        }).catch(() => {
          data.isMyFavorites = !data.isMyFavorites
          this.crud.notify('操作失败!')
        })
      }).catch(() => {
        data.isMyFavorites = !data.isMyFavorites
        this.crud.notify('操作失败!')
      })
    },
    // 图片上传完成之后的展示
    cropUploadSuccess(jsonData, field) {
      this.form.targetPic = jsonData.content
      console.log('上传成功!', jsonData.content)
    },
    // 上传图片文本框是否打开
    toggleShow() {
      this.picshow = !this.picshow
    },
    checkboxT(row, rowIndex) {
      return row.id >= 1
    },
    // 如果新增，则设置form为空
    [CRUD.HOOK.afterToCU](crud, form) {
      if (crud.status.add === 1) {
        this.form = {}
      } else if (crud.status.edit === 1) {
        console.log('form id ', form)
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
      console.log('提交', crud)
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
