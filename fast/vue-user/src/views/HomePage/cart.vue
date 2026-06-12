<template>
  <div class="cart-container">

    <!-- 购物车图书列表 -->
    <div class="cart-content" v-if="cartList.length > 0">
      <div class="cart-items">
        <div v-for="item in cartList"
             :key="item.cartId"
             class="cart-item"
        >
          <el-checkbox :model-value="isSelected(item.cartId)"
                       @change="(val) => updateItemSelection(val, item)"
          />

          <div class="item-cover">
            <img :src="baseUrl + item.cover" alt="">
          </div>

          <div class="item-info">
            <h3 class="item-title">
              <a @click="toGoDetail(item.bookId)">
                {{ item.title }}
              </a>
            </h3>
            <p class="item-author">{{ item.author }}</p>
          </div>

          <div class="item-price">
            ¥{{ item.price }}
          </div>

          <div class="item-quantity">
            <el-input-number v-model="item.quantity"
                             :min="1"
                             :max="99"
                             @change="updateQuantity(item)"
            />
          </div>

          <div class="item-total">
            ¥{{ (item.price * item.quantity).toFixed(2) }}
          </div>

          <div class="item-action">
            <el-button type="danger"
                       icon="Delete"
                       circle
                       @click="removeItem(item.cartId)"
            />
          </div>
        </div>
      </div>

      <!-- 购物车结算 -->
      <div class="cart-summary">
        <div class="summary-content">
          <div class="summary-row">
            <span>总计: </span>
            <span class="summary-total">¥{{ totalPrice.toFixed(2) }}</span>
          </div>
          <div class="summary-row">
            <span>已选 {{ selectedCount }} 本图书</span>
          </div>
          <div class="summary-actions">
            <el-button type="primary"
                       size="large"
                       @click="checkout"
                       :disabled="selectedCount === 0"
            >
              结算
            </el-button>
          </div>
        </div>
      </div>

    </div>

  </div>
</template>

<script setup>
import {onMounted, ref, computed} from 'vue'
import {delCart, selectMyCartList, updateCart} from "@/api/mall/cart.js";
import {useRouter} from "vue-router";

//基础API地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//购物车列表数据
const cartList = ref([])

//路由实例
const router = useRouter()

//选中商品的ID集合
const selectedItems = ref(new Set())

//计算选中商品的总价格
const totalPrice = computed(() => {
  return cartList.value
      .filter(item => isSelected(item.cartId)) //过滤出选中的商品
      .reduce((total, item) => total + (item.price * item.quantity), 0)
})

//计算选中商品的总数量
const selectedCount = computed(() => {
  return cartList.value
      .filter(item => isSelected(item.cartId)) //过滤出选中的商品
      .reduce((count, item) => count + item.quantity, 0)
})

//结算
const checkout = () => {
  //获取已选中的商品列表
  const selectedBooks = cartList.value.filter(item => isSelected(item.cartId))
  //将选中的商品数据传递到结算页面
  router.push({
    path: '/index/checkout',
    query: {
      products: JSON.stringify(selectedBooks)
    }
  })
}

//判断指定商品是否被选中
const isSelected = (cartId) => {
  return selectedItems.value.has(cartId)
}

//更新商品数量
const updateQuantity = (item) => {
  updateCart(item)
}

//更新商品的选中状态
const updateItemSelection = (selected, item) => {
  if (selected) {
    //添加到选中集合
    selectedItems.value.add(item.cartId)
  } else {
    //从选中集合中移出
    selectedItems.value.delete(item.cartId)
  }
}

//移除商品
const removeItem = (cartId) => {
  delCart(cartId).then(res => {
    getList()
  })
}

//跳转到图书详情页面
const toGoDetail = (bookId) => {
  router.push(`/index/bookDetail/${bookId}`)
}

//查询数据
const getList = () => {
  selectMyCartList().then(res => {
    cartList.value = res.data
  })
}

onMounted(() => {
  getList()
})

</script>

<style scoped>
/* 购物车容器样式 */
.cart-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
  background-color: #fdfbf7; /* 浅黄色背景，营造温暖感 */
  min-height: 100vh; /* 最小高度为视口高度，确保撑满页面 */
}

/* 购物车头部样式 */
.cart-header {
  margin-bottom: 30px; /* 底部外边距 */
}

.cart-header p {
  color: #7f8c8d; /* 灰色文字 */
  margin: 0; /* 移除默认外边距 */
}

/* 购物车内容区域（有商品时） */
.cart-content {
  display: flex; /* 使用弹性布局 */
  gap: 30px; /* 子元素之间的间距 */
}

/* 购物车商品列表区域 */
.cart-items {
  flex: 1; /* 占据剩余空间 */
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角边框 */
  padding: 20px; /* 内边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果，增强立体感 */
}

/* 单个商品项样式 */
.cart-item {
  display: flex; /* 使用弹性布局 */
  align-items: center; /* 垂直居中 */
  gap: 20px; /* 子元素之间的间距 */
  padding: 20px 0; /* 上下内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框分隔线 */
}

/* 最后一个商品项移除底部边框 */
.cart-item:last-child {
  border-bottom: none;
}

/* 商品封面容器样式 */
.item-cover {
  width: 80px; /* 固定宽度 */
  height: 100px; /* 固定高度，适合图书比例 */
  border-radius: 4px; /* 轻微圆角 */
  overflow: hidden; /* 超出部分隐藏 */
  background: #f5f5f5; /* 浅灰色背景 */
  display: flex; /* 使用弹性布局居中图片 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

/* 商品封面图片样式 */
.item-cover img {
  max-width: 100%; /* 最大宽度为容器宽度 */
  max-height: 100%; /* 最大高度为容器高度 */
  object-fit: contain; /* 保持图片比例，完整显示图片 */
}

/* 商品信息区域 */
.item-info {
  flex: 1; /* 占据剩余空间 */
}

/* 商品标题样式 */
.item-title {
  font-size: 16px; /* 字体大小 */
  font-weight: 600; /* 字体粗细，中等粗体 */
  margin: 0 0 8px 0; /* 底部外边距 */
  color: #2c3e50; /* 深蓝色文字，增加可读性 */
}

/* 商品作者信息样式 */
.item-author {
  font-size: 13px; /* 较小的字体大小 */
  color: #7f8c8d; /* 灰色文字，次要信息 */
  margin: 0; /* 移除默认外边距 */
}

/* 商品单价样式 */
.item-price {
  width: 100px; /* 固定宽度 */
  font-weight: 500; /* 中等字体粗细 */
  color: #2c3e50; /* 深蓝色文字 */
  margin-right: 20px; /* 右侧外边距，增加与其他元素的距离 */
}

/* 商品数量选择器容器 */
.item-quantity {
  width: 120px; /* 固定宽度 */
  margin-right: 20px; /* 右侧外边距，增加与其他元素的距离 */
}

/* 商品小计样式 */
.item-total {
  width: 100px; /* 固定宽度 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色，突出显示金额 */
  margin-right: 20px; /* 右侧外边距，增加与其他元素的距离 */
}

/* 商品操作区域（删除按钮） */
.item-action {
  width: 50px; /* 固定宽度 */
  text-align: center; /* 文字居中 */
}

/* 购物车结算区域 */
.cart-summary {
  width: 300px; /* 固定宽度 */
}

/* 结算内容区域 */
.summary-content {
  position: sticky; /* 粘性定位，滚动时保持可见 */
  top: 20px; /* 距离顶部20px */
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角边框 */
  padding: 25px; /* 内边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 结算信息行样式 */
.summary-row {
  display: flex; /* 使用弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  margin-bottom: 15px; /* 底部外边距 */
}

/* 总计金额样式 */
.summary-total {
  font-size: 24px; /* 较大字体大小 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色，突出显示总金额 */
}

/* 结算操作区域 */
.summary-actions {
  margin-top: 20px; /* 顶部外边距，与上方内容分隔 */
}

/* 结算按钮样式 */
.summary-actions .el-button {
  width: 100%; /* 宽度100%，占满容器 */
}

/* 购物车为空时的样式 */
.cart-empty {
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角边框 */
  padding: 80px 20px; /* 较大的内边距，使内容居中 */
  text-align: center; /* 文字居中 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 空购物车图标样式 */
.empty-content .el-icon {
  margin-bottom: 20px; /* 底部外边距 */
  color: #bdc3c7; /* 浅灰色图标 */
}

/* 空购物车标题样式 */
.empty-content h3 {
  font-size: 22px; /* 字体大小 */
  color: #2c3e50; /* 深蓝色文字 */
  margin: 0 0 10px 0; /* 底部外边距 */
}

/* 空购物车描述文字样式 */
.empty-content p {
  color: #7f8c8d; /* 灰色文字 */
  margin: 0 0 30px 0; /* 底部外边距 */
}
</style>
