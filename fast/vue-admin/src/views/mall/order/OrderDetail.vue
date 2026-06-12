<template>
  <vxe-modal title="订单详情" v-model="open" width="50%" show-maximize showFooter resize>
    <el-descriptions
        :column="2"
        border
    >
      <el-descriptions-item>
        <template #label>
          订单号
        </template>
        {{ order.orderId }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          订单总价
        </template>
        {{ order.totalAmount }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          收货人
        </template>
        {{ order.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          联系电话
        </template>
        {{ order.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          收货地址
        </template>
        {{ order.address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          订单状态
        </template>
        <el-tag type="warning" v-if="order.status === '待付款'">{{ order.status }}</el-tag>
        <el-tag type="info" v-if="order.status === '待发货'">{{ order.status }}</el-tag>
        <el-tag type="primary" v-if="order.status === '已发货'">{{ order.status }}</el-tag>
        <el-tag type="success" v-if="order.status === '已完成'">{{ order.status }}</el-tag>
        <el-tag type="danger" v-if="order.status === '已取消'">{{ order.status }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <br>

    <el-table :data="order.orderBookList" height="30vh">
      <el-table-column label="序号" align="center" type="index" width="50"/>
      <el-table-column label="图书名称" prop="title"/>
      <el-table-column label="封面" align="center" prop="cover">
        <template #default="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" prop="author"/>
      <el-table-column label="价格" prop="price"/>
      <el-table-column label="数量" prop="quantity"/>
    </el-table>

  </vxe-modal>
</template>

<script setup>
import {getOrder} from "@/api/mall/order.js";


//对话框是否打开
const open = ref(false)

//订单数据
const order = ref({})

//父组件调用方法
const handleOpen = (orderId) => {
  getOrder(orderId).then(res => {
    order.value = res.data
    //打开对话框
    open.value = true
  })
}

//暴露方法
defineExpose({
  handleOpen
})
</script>

<style scoped>

</style>
