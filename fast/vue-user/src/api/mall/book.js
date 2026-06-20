import request from '@/utils/request'

// 查询图书列表
export function listBook(query) {
  return request({
    url: '/mall/book/list',
    method: 'get',
    params: query
  })
}

// 查询图书详细
export function getBook(bookId) {
  return request({
    url: '/mall/book/' + bookId,
    method: 'get'
  })
}

// 新增图书
export function addBook(data) {
  return request({
    url: '/mall/book',
    method: 'post',
    data: data
  })
}

// 修改图书
export function updateBook(data) {
  return request({
    url: '/mall/book',
    method: 'put',
    data: data
  })
}

// 删除图书
export function delBook(bookId) {
  return request({
    url: '/mall/book/' + bookId,
    method: 'delete'
  })
}

// 查询推荐的图书
export function selectRecommendBook() {
    return request({
        url: '/mall/book/selectRecommendBook',
        method: 'get'
    })
}

// 查询销售数量前5的图书以及销售额
export function selectTopFiveBooks() {
  return request({
    url: '/mall/book/selectTopFiveBooks',
    method: 'get'
  })
}