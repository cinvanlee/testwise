import request from '@/utils/request'

// 答卷
export function addPaperRecord(data) {
  return request({
    url: '/education/paperRecord',
    method: 'post',
    data: data
  })
}
// 答题记录列表
export function listRecord(query) {
  return request({
    url: '/education/paperRecord/list',
    method: 'get',
    params: query
  })
}
// 详细信息
export function getRecordById(id) {
  return request({
    url: '/education/paperRecord/getById/' + id,
    method: 'get'
  })
}
// 批改
export function editPaperRecord(data) {
  return request({
    url: '/education/paperRecord/edit',
    method: 'post',
    data: data
  })
}

// 错误记录
export function errorListRecord(query) {
  return request({
    url: '/education/paperRecord/errorList',
    method: 'get',
    params: query
  })
}
