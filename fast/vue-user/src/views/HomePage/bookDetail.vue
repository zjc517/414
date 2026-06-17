<template>
  <div class="book-detail-container">
    <!-- 返回按钮 -->
    <div class="return-button">
      <el-button @click="router.go(-1)">
        <el-icon>
          <ArrowLeft/>
        </el-icon>
        返回
      </el-button>
    </div>

    <!-- 图书信息 -->
    <div class="book-detail-content">
      <div class="book-cover-section">
        <div class="book-cover-wrapper">
          <img :src="baseUrl + book.cover" alt="">
        </div>
      </div>

      <div class="book-info-section">
        <h1 class="book-title">{{ book.title }}</h1>

        <div class="book-authors">
          <span class="label">作者: </span>
          <span class="authors">{{ book.author }}</span>
        </div>

        <div class="book-price-section">
          <div class="current-price">
            <span class="label">价格: </span>
            <span class="price">¥{{ book.price }}</span>
          </div>
        </div>

        <div class="book-actions">
          <el-button color="#2c3e50" size="large" class="cart-btn" @click="addToCart()">
            <el-icon>
              <shopping-cart/>
            </el-icon>
            加入购物车
          </el-button>
          <el-button type="primary" size="large" class="buy-btn" @click="">
            立即购买
          </el-button>
        </div>

        <div class="book-description">
          <h3>图书简介</h3>
          <p>{{ book.description }}</p>
        </div>

      </div>
    </div>

    <!-- 用户评价区域 -->
    <div class="book-details-section" v-if="evaluateList.length > 0">
      <div v-for="review in evaluateList"
           :key="review.evaluateId"
           class="review-item">
        <div class="review-header">
          <span class="userName">{{ review.userName }}</span>
          <el-rate v-model="review.rating" disabled size="large"/>
        </div>
        <p class="review-content">{{ review.content }}</p>
        <div class="review-date">{{ review.createTime }}</div>
      </div>
    </div>

  </div>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {ArrowLeft, ShoppingCart} from "@element-plus/icons-vue";
import {getBook} from "@/api/mall/book.js";
import {addCart} from "@/api/mall/cart.js";
import {ElMessage} from "element-plus";
import {selectEvaluateListByBookId} from "@/api/mall/evaluate.js";

//基础API地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//路由实例
const router = useRouter()
const route = useRoute()

//将图书加入到购物车中
const addToCart = () => {
  const item = {
    bookId: book.value.bookId,
    quantity: 1
  }
  addCart(item).then(res => {
    ElMessage.success('添加成功~')
  })
}

//图书信息
const book = ref({})

//评价列表
const evaluateList = ref([])

onMounted(() => {
  //根据图书ID查询图书信息
  getBook(route.params.id).then(res => {
    book.value = res.data
  })

  //根据图书ID查询评价列表
  selectEvaluateListByBookId(route.params.id).then(res => {
    evaluateList.value = res.data
  })

})

</script>

<style scoped>
/* 主容器样式 */
.book-detail-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
  background-color: #fdfbf7; /* 背景色：米白色，增加阅读舒适度 */
  min-height: 100vh; /* 最小高度：全视口高度 */
  color: #2c3e50; /* 主文字颜色：深蓝色 */
}

/* 返回按钮区域 */
.return-button {
  margin-bottom: 20px; /* 底部外边距 */
}

/* 图书详情主要内容区域 */
.book-detail-content {
  display: flex; /* 使用flex布局 */
  gap: 40px; /* 子元素间距 */
  margin-bottom: 40px; /* 底部外边距 */
  padding: 30px; /* 内边距 */
  background: #fff; /* 背景色：白色 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果，增加立体感 */
}

/* 图书封面区域 */
.book-cover-section {
  flex: 0 0 300px; /* 固定宽度300px，不伸缩 */
}

/* 封面包装器 */
.book-cover-wrapper {
  width: 100%; /* 宽度100% */
  border-radius: 4px; /* 圆角 */
  overflow: hidden; /* 隐藏溢出内容 */
  background: #f5f5f5; /* 背景色：浅灰色，图片加载前显示 */
  display: flex; /* flex布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

/* 封面图片样式 */
.book-cover-wrapper img {
  max-width: 100%; /* 最大宽度100% */
  max-height: 100%; /* 最大高度100% */
  object-fit: contain; /* 保持图片比例，完整显示 */
}

/* 图书信息区域 */
.book-info-section {
  flex: 1; /* 占据剩余空间 */
}

/* 图书标题样式 */
.book-title {
  font-size: 28px; /* 字体大小 */
  margin: 0 0 20px; /* 底部外边距 */
  color: #1a1a1a; /* 颜色：深黑色 */
  font-weight: bold; /* 粗体 */
}

/* 作者信息样式 */
.book-authors {
  margin-bottom: 15px; /* 底部外边距 */
  font-size: 16px; /* 字体大小 */
}

/* 标签样式（用于"作者："、"价格："等标签） */
.label {
  font-weight: bold; /* 粗体 */
  color: #555; /* 颜色：中灰色 */
}

/* 作者名字样式 */
.authors {
  color: #2c3e50; /* 颜色：深蓝色 */
}

/* 价格区域样式 */
.book-price-section {
  margin-bottom: 20px; /* 底部外边距 */
  padding: 15px 0; /* 上下内边距 */
  border-top: 1px solid #eee; /* 上边框 */
  border-bottom: 1px solid #eee; /* 下边框 */
}

/* 当前价格显示区域 */
.current-price {
  display: flex; /* flex布局 */
  align-items: center; /* 垂直居中 */
  gap: 10px; /* 子元素间距 */
}

/* 价格数字样式 */
.price {
  font-size: 24px; /* 字体大小 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 颜色：红色，突出价格 */
}

/* 操作按钮区域 */
.book-actions {
  display: flex; /* flex布局 */
  gap: 15px; /* 按钮间距 */
  margin-bottom: 30px; /* 底部外边距 */
}

/* 购物车和购买按钮通用样式 */
.cart-btn, .buy-btn {
  flex: 1; /* 均分剩余空间 */
  padding: 15px; /* 内边距 */
}

/* 图书描述标题样式 */
.book-description h3 {
  margin-top: 0; /* 上边距归零 */
  margin-bottom: 15px; /* 底部外边距 */
  font-size: 18px; /* 字体大小 */
  color: #2c3e50; /* 颜色：深蓝色 */
}

/* 图书描述内容样式 */
.book-description p {
  line-height: 1.8; /* 行高，增加可读性 */
  color: #555; /* 颜色：中灰色 */
  text-align: justify; /* 两端对齐 */
}

/* 用户评价区域 */
.book-details-section {
  background: #fff; /* 背景色：白色 */
  border-radius: 8px; /* 圆角 */
  padding: 20px; /* 内边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 单个评价项样式 */
.review-item {
  padding: 20px 0; /* 上下内边距 */
  border-bottom: 1px solid #f5f5f5; /* 底部边框，浅灰色分隔线 */
}

/* 最后一个评价项去除底部边框 */
.review-item:last-child {
  border-bottom: none;
}

/* 评价头部样式 */
.review-header {
  display: flex; /* flex布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  margin-bottom: 10px; /* 底部外边距 */
}

/* 用户名样式 */
.userName {
  font-weight: bold; /* 粗体 */
  color: #2c3e50; /* 颜色：深蓝色 */
}

/* 评价内容样式 */
.review-content {
  color: #555; /* 颜色：中灰色 */
  line-height: 1.6; /* 行高 */
  margin-bottom: 10px; /* 底部外边距 */
}

/* 评价日期样式 */
.review-date {
  font-size: 12px; /* 小字体 */
  color: #999; /* 颜色：浅灰色 */
}
</style>
