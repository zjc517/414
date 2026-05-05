import request from '@/utils/request'

// 查询图书分类列表
export function listCategory(query) {
    return request({
        url: '/mall/category/list',
        method: 'get',
        params: query
    })
}