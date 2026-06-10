import request from '@/utils/request'

// 查询收货地址列表
export function listAddress(query) {
  return request({
    url: '/mall/address/list',
    method: 'get',
    params: query
  })
}

// 查询收货地址详细
export function getAddress(addressId) {
  return request({
    url: '/mall/address/' + addressId,
    method: 'get'
  })
}

// 新增收货地址
export function addAddress(data) {
  return request({
    url: '/mall/address',
    method: 'post',
    data: data
  })
}

// 修改收货地址
export function updateAddress(data) {
  return request({
    url: '/mall/address',
    method: 'put',
    data: data
  })
}

// 删除收货地址
export function delAddress(addressId) {
  return request({
    url: '/mall/address/' + addressId,
    method: 'delete'
  })
}

// 查询用户个人的收货地址列表
export function selectMyAddress() {
  return request({
    url: '/mall/address/selectMyAddress',
    method: 'get'
  })
}