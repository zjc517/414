<template>
  <div class="order-container">
    <!-- 订单状态筛选 -->
    <div class="order-filter">
      <el-tabs v-model="activeTab" @tabChange="handleTabChange">
        <el-tab-pane label="全部订单" name="all"/>
        <el-tab-pane label="待付款" name="待付款"/>
        <el-tab-pane label="待发货" name="待发货"/>
        <el-tab-pane label="已发货" name="已发货"/>
        <el-tab-pane label="已完成" name="已完成"/>
        <el-tab-pane label="已取消" name="已取消"/>
      </el-tabs>
    </div>

    <!-- 订单列表 -->
    <div class="order-content" v-if="orderList.length > 0">
      <div v-for="order in orderList" :key="order.orderId" class="order-item">
        <div class="order-head">
          <div class="order-info">
            <span class="order-no">订单号: {{ order.orderId }}</span>
            <span class="order-time">{{ order.createTime }}</span>
          </div>
          <div class="order-status">
            <el-tag type="warning" v-if="order.status === '待付款'">{{ order.status }}</el-tag>
            <el-tag type="info" v-if="order.status === '待发货'">{{ order.status }}</el-tag>
            <el-tag type="primary" v-if="order.status === '已发货'">{{ order.status }}</el-tag>
            <el-tag type="success" v-if="order.status === '已完成'">{{ order.status }}</el-tag>
            <el-tag type="danger" v-if="order.status === '已取消'">{{ order.status }}</el-tag>
          </div>
        </div>

        <div class="order-address">
          <div class="address-detail">
            <div class="address-person">
              <span class="address-name">{{ order.name }}</span>
              <span class="address-phone">{{ order.phone }}</span>
            </div>
            <div class="address-full">{{ order.address }}</div>
          </div>
        </div>

        <div class="order-body">
          <div v-for="item in order.orderBookList"
               :key="item.orderBookId"
               class="order-product"
               @click="toGoDetail(item.bookId)"
          >
            <div class="product-cover">
              <img :src="baseUrl + item.cover" alt="">
            </div>
            <div class="product-info">
              <div class="product-title">{{ item.title }}</div>
              <div class="product-author">{{ item.author }}</div>
            </div>
            <div class="product-price">¥{{ item.price }}</div>
            <div class="product-quantity">×{{ item.quantity }}</div>
            <div class="product-total">¥{{ (item.price * item.quantity).toFixed(2) }}</div>
            <div class="product-actions" v-if="order.status === '已完成'">
              <el-button type="success" size="small" @click.stop="">
                图书评价
              </el-button>
            </div>
          </div>
        </div>

        <div class="order-foot">
          <div class="order-total">
            共 {{ order.orderBookList.length }} 件商品, 合计:
            <span class="total-price">¥{{ order.totalAmount.toFixed(2) }}</span>
          </div>
          <div class="order-actions">
            <el-button v-if="order.status === '待付款'"
                       type="danger"
                       size="small"
                       @click="cancelOrder(order)"
            >
              取消订单
            </el-button>
            <el-button v-if="order.status === '待付款'"
                       type="primary"
                       size="small"
                       @click="payOrder(order)"
            >
              立即付款
            </el-button>
            <el-button v-if="order.status === '已发货'"
                       type="primary"
                       size="small"
                       @click=""
            >
              确认收货
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 无订单状态 -->
    <div class="order-empty" v-else>
      <div class="empty-content">
        <el-icon size="60"><Document/></el-icon>
        <h3>暂无订单</h3>
        <el-button type="primary" @click="router.push('/index/books')">去逛逛</el-button>
      </div>
    </div>


  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {payment, selectMyOrderList, updateOrder} from "@/api/mall/order.js";
import {useRouter} from "vue-router";
import {Document} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

//基础API地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//当前激活的标签页
const activeTab = ref('all')

//订单列表数据
const orderList = ref([])

//查询参数
const query = ref({
  pageNum: 1,
  pageSize: 10,
  status: null
})

//数据总数
const total = ref(0)

const router = useRouter()

//付款
const payOrder = (order) => {
  ElMessageBox.confirm('确定要支付该订单吗？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    payment(order.orderId).then(res => {
      getList()
      ElMessage.success('付款成功~')
    })
  })
}

//取消订单
const cancelOrder = (order) => {
  const item = {
    orderId: order.orderId,
    status: '已取消',
    orderBookList: order.orderBookList
  }
  ElMessageBox.confirm('确定要取消订单吗？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    updateOrder(item).then(res => {
      getList()
      ElMessage.success('成功取消订单')
    })
  })
}

//跳转到图书详情页面
const toGoDetail = (bookId) => {
  router.push(`/index/bookDetail/${bookId}`)
}

//处理标签页切换
const handleTabChange = () => {
  query.value.pageNum = 1
  getList()
}

const getList = () => {
  //根据当前标签设置状态参数查询
  if (activeTab.value === 'all') {
    query.value.status = null
  } else {
    query.value.status = activeTab.value
  }

  selectMyOrderList(query.value).then(res => {
    orderList.value = res.rows
    total.value = res.total
  })
}

onMounted(() => {
  getList()
})

</script>

<style scoped>
/*
 * 订单页面主容器
 * max-width: 最大宽度限制
 * margin: 水平居中
 * padding: 内边距
 * background-color: 背景色
 * min-height: 最小高度，确保占满视口
 */
.order-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fdfbf7; /* 浅米色背景，营造温馨感 */
  min-height: 100vh;
}

/* 订单筛选区域样式 */
.order-filter {
  border-radius: 8px; /* 圆角 */
  padding: 0 20px; /* 左右内边距 */
  margin-bottom: 10px; /* 底部外边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 订单内容区域 - 使用 flex 布局垂直排列 */
.order-content {
  display: flex;
  flex-direction: column; /* 垂直方向排列 */
  gap: 20px; /* 子元素间距 */
}

/* 单个订单项样式 */
.order-item {
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影 */
  overflow: hidden; /* 隐藏溢出内容 */
}

/* 订单头部样式 */
.order-head {
  display: flex;
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  padding: 15px 20px; /* 内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框 */
  background-color: #fafafa; /* 浅灰色背景 */
}

/* 订单信息区域 */
.order-info {
  display: flex;
  gap: 20px; /* 子元素间距 */
}

/* 订单号样式 */
.order-no {
  font-weight: 500; /* 中等字体粗细 */
  color: #2c3e50; /* 深蓝色文字 */
}

/* 订单时间样式 */
.order-time {
  color: #7f8c8d; /* 灰色文字 */
}

/* 订单状态标签 */
.order-status {
  font-weight: 500;
}

/* 收货地址区域 */
.order-address {
  padding: 10px 1px; /* 上下内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框 */
  background-color: #fafafa; /* 浅灰色背景 */
}

/* 地址详情 */
.address-detail {
  padding-left: 20px; /* 左内边距 */
}

/* 收货人信息 */
.address-person {
  margin-bottom: 5px; /* 底部外边距 */
}

/* 收货人姓名 */
.address-name {
  font-weight: bold; /* 粗体 */
  margin-right: 15px; /* 右外边距 */
}

/* 收货人电话 */
.address-phone {
  color: #666; /* 深灰色文字 */
}

/* 详细地址 */
.address-full {
  color: #666; /* 深灰色文字 */
  font-size: 14px; /* 较小字体 */
}

/* 订单商品区域 */
.order-body {
  padding: 20px; /* 内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框 */
}

/* 单个商品样式 */
.order-product {
  display: flex;
  align-items: center; /* 垂直居中 */
  gap: 20px; /* 子元素间距 */
  padding: 15px 0; /* 上下内边距 */
  cursor: pointer; /* 手型光标 */
}

/* 商品悬停效果 */
.order-product:hover {
  background-color: #f9f9f9; /* 浅灰色背景 */
}

/* 商品封面容器 */
.product-cover {
  width: 60px;
  height: 80px;
  border-radius: 4px; /* 小圆角 */
  overflow: hidden; /* 隐藏溢出部分 */
  background: #f5f5f5; /* 浅灰色背景 */
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

/* 商品封面图片 */
.product-cover img {
  max-width: 100%; /* 最大宽度100% */
  max-height: 100%; /* 最大高度100% */
  object-fit: contain; /* 保持比例，完整显示 */
}

/* 商品信息区域 */
.product-info {
  flex: 1; /* 占据剩余空间 */
}

/* 商品标题 */
.product-title {
  font-weight: 600; /* 较粗字体 */
  margin-bottom: 5px; /* 底部外边距 */
  color: #2c3e50; /* 深蓝色文字 */
}

/* 商品作者 */
.product-author {
  font-size: 13px; /* 较小字体 */
  color: #7f8c8d; /* 灰色文字 */
}

/* 商品单价 */
.product-price {
  width: 100px; /* 固定宽度 */
  color: #2c3e50; /* 深蓝色文字 */
}

/* 商品数量 */
.product-quantity {
  width: 50px; /* 固定宽度 */
  text-align: center; /* 文字居中 */
  color: #666; /* 深灰色文字 */
}

/* 商品小计 */
.product-total {
  width: 100px; /* 固定宽度 */
  text-align: right; /* 文字右对齐 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色，突出价格 */
}

/* 商品操作按钮区域 */
.product-actions {
  width: 100px; /* 固定宽度 */
  text-align: center; /* 文字居中 */
}

/* 订单底部区域 */
.order-foot {
  display: flex;
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  padding: 20px; /* 内边距 */
}

/* 订单总计 */
.order-total {
  font-size: 16px; /* 字体大小 */
}

/* 总计价格 */
.total-price {
  font-size: 18px; /* 较大字体 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色，突出价格 */
}

/* 订单操作按钮区域 */
.order-actions {
  display: flex;
  gap: 10px; /* 按钮间距 */
}

/* 空状态区域 */
.order-empty {
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角 */
  padding: 80px 20px; /* 内边距 */
  text-align: center; /* 文字居中 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影 */
}

/* 空状态图标 */
.empty-content .el-icon {
  margin-bottom: 20px; /* 底部外边距 */
  color: #bdc3c7; /* 浅灰色 */
}

/* 空状态标题 */
.empty-content h3 {
  font-size: 22px; /* 较大字体 */
  color: #2c3e50; /* 深蓝色文字 */
  margin: 0 0 10px 0; /* 底部外边距 */
}

/* 空状态描述 */
.empty-content p {
  color: #7f8c8d; /* 灰色文字 */
  margin: 0 0 30px 0; /* 底部外边距 */
}

/* 分页容器 */
.pagination-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  padding: 30px 0 20px; /* 上下内边距 */
}

/* ========== 评价弹窗样式 ========== */

/* 评价表单容器 */
.book-evaluate-form {
  padding: 20px 0; /* 上下内边距 */
}

/* 图书基本信息区域 */
.book-basic-info {
  display: flex;
  gap: 20px; /* 子元素间距 */
  padding: 15px; /* 内边距 */
  background-color: #f9f9f9; /* 浅灰色背景 */
  border-radius: 4px; /* 圆角 */
  margin-bottom: 20px; /* 底部外边距 */
}

/* 图书封面 */
.book-cover {
  width: 80px; /* 比列表中的稍大 */
  height: 100px;
  border-radius: 4px; /* 圆角 */
  overflow: hidden; /* 隐藏溢出 */
  background: #f5f5f5; /* 浅灰色背景 */
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

/* 图书封面图片 */
.book-cover img {
  max-width: 100%; /* 最大宽度100% */
  max-height: 100%; /* 最大高度100% */
  object-fit: contain; /* 保持比例 */
}

/* 图书文字信息 */
.book-text-info {
  flex: 1; /* 占据剩余空间 */
}

/* 图书标题 */
.book-title {
  font-size: 18px; /* 较大字体 */
  margin: 0 0 10px 0; /* 底部外边距 */
  color: #2c3e50; /* 深蓝色 */
  font-weight: bold; /* 粗体 */
}

/* 图书作者和价格 */
.book-author,
.book-price {
  margin: 5px 0; /* 上下外边距 */
  color: #666; /* 深灰色 */
}

/* 评价表单区域 */
.evaluate-section {
  padding: 10px 0; /* 上下内边距 */
}
</style>
