import request from '@/utils/request'

// 查询图书分类列表
export function listCategory(query) {
    return request({
        url: '/mall/category/list',
        method: 'get',
        params: query
    })
}
export function getCategory(categoryId) {
    return request({
        url: '/mall/category/' + categoryId,
        method: 'get'
    })
}

// 新增图书分类
export function addCategory(data) {
    return request({
        url: '/mall/category',
        method: 'post',
        data: data
    })
}

// 修改图书分类
export function updateCategory(data) {
    return request({
        url: '/mall/category',
        method: 'put',
        data: data
    })
}

// 删除图书分类
export function delCategory(categoryId) {
    return request({
        url: '/mall/category/' + categoryId,
        method: 'delete'
    })
}