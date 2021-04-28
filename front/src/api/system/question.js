import request from '@/utils/request'
export function select(data) {
  return request({
    url: 'api/mockexam/question',
    method: 'get',
    data
  })
}
export function add(data) {
  return request({
    url: 'api/mockexam/question',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: 'api/mockexam/question',
    method: 'put',
    data
  })
}
export function del(data) {
  return request({
    url: 'api/mockexam/question',
    method: 'delete',
    data
  })
}
export function questionSelectById(params) {
  return request({
    url: 'api/mockexam/question/selectById',
    method: 'get',
    params
  })
}

export function questionSelectRandomQuestion(data) {
  return request({
    url: 'api/mockexam/question/selectRandomQuestion',
    method: 'post',
    data
  })
}
export function questionSelectIds(params) {
  return request({
    url: 'api/mockexam/question/selectIds',
    method: 'get',
    params
  })
}
export function questionChooseAnswer(params) {
  return request({
    url: 'api/mockexam/question/chooseAnswer',
    method: 'get',
    params
  })
}
export function questionSelectCount(data) {
  return request({
    url: 'api/mockexam/question/selectCount',
    method: 'post',
    data
  })
}
export function questionSelectExamQuestion(params) {
  return request({
    url: 'api/mockexam/question/selectExamQuestion',
    method: 'get',
    params
  })
}
export function questionSelectByObjectiveType(data) {
  return request({
    url: 'api/mockexam/question/selectByObjectiveType',
    method: 'post',
    data
  })
}
export function favoritesSelectByPage(data) {
  return request({
    url: 'api/mockexam/favorites/selectByPage',
    method: 'post',
    data
  })
}
export function favoritesOperation(data) {
  return request({
    url: 'api/mockexam/favorites/operation',
    method: 'post',
    data
  })
}
export function wrongQuestionSelectByPage(data) {
  return request({
    url: 'api/mockexam/wrongQuestion/selectByPage',
    method: 'post',
    data
  })
}
export function userBehaviorSelectByPage(data) {
  return request({
    url: 'api/mockexam/user/behavior/selectByPage',
    method: 'post',
    data
  })
}
export function userBehaviorConfirmRead(data) {
  return request({
    url: 'api/mockexam/user/behavior/confirmRead',
    method: 'post',
    data
  })
}
export function userBehaviorJudgeIsRead(data) {
  return request({
    url: 'api/mockexam/user/behavior/judgeIsRead',
    method: 'post',
    data
  })
}
export function userBehaviorLastLoginTime(data) {
  return request({
    url: 'api/mockexam/user/behavior/lastLoginTime',
    method: 'post',
    data
  })
}
export function updateLoginTime(data) {
  return request({
    url: 'api/mockexam/user/behavior/updateLoginTime',
    method: 'post',
    data
  })
}
export default { select, add, edit, del, questionSelectById, questionSelectRandomQuestion, questionSelectIds, questionChooseAnswer,
  questionSelectCount, questionSelectExamQuestion, questionSelectByObjectiveType, favoritesSelectByPage, favoritesOperation, wrongQuestionSelectByPage,
  userBehaviorSelectByPage, userBehaviorConfirmRead, userBehaviorJudgeIsRead, userBehaviorLastLoginTime, updateLoginTime }
