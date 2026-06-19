<template>
  <!-- 主容器 -->
  <div class="container">
    <!-- 顶部推荐区域 -->
    <section class="hero-section">
      <!-- 左侧文本内容 -->
      <div class="hero-content">
        <!-- 推荐标签 -->
        <div class="hero-tag">Editor's Pick · 今日店长推荐</div>
        <!-- 书籍标题 -->
        <h1 class="hero-title">{{ recommendBook.title }}</h1>
        <!-- 书籍描述 -->
        <p class="hero-desc">{{ recommendBook.description }}</p>
        <!-- 作者信息 -->
        <div class="hero-meta">
          <span class="author">by: {{ recommendBook.author }}</span>
        </div>
        <!-- 操作按钮 -->
        <div class="hero-actions">
          <!-- 加入购物车按钮 -->
          <el-button color="#2c3e50" size="large" class="read-btn" @click="addToCart">
            加入购物车
          </el-button>
          <!-- 查看详情按钮 -->
          <el-button text bg size="large" @click="viewBookDetail(recommendBook.bookId)">
            查看信息
          </el-button>
        </div>
      </div>
      <!-- 右侧图片区域 -->
      <div class="hero-image-box">
        <!-- 书籍封面图片 -->
        <img :src="baseUrl + recommendBook.cover" alt="Featured Book"/>
        <!-- 装饰性圆形背景 -->
        <div class="decorative-circle"></div>
      </div>
    </section>

    <!-- 主要内容区域布局 -->
    <div class="main-layout">
      <!-- 左侧内容区域：新书列表 -->
      <div class="content-left">
        <!-- 区域标题 -->
        <div class="section-header">
          <h2>Fresh Arrivals <span class="sub-header">新书速递</span></h2>
          <!-- 查看更多链接 -->
          <el-link :underline="false">查看全部
            <el-icon>
              <ArrowRight/>
            </el-icon>
          </el-link>
        </div>

        <!-- 书籍网格布局 -->
        <div class="book-grid">
          <!-- 循环渲染新书列表 -->
          <div
              v-for="book in newBookList"
              :key="book.bookId"
              class="aesthetic-card"
              @click="viewBookDetail(book.bookId)"
          >
            <!-- 书籍封面容器 -->
            <div class="card-cover-wrapper">
              <img :src="baseUrl + book.cover" loading="lazy"/>
            </div>
            <!-- 书籍信息 -->
            <div class="card-info">
              <!-- 标题和价格行 -->
              <div class="title-price-row">
                <h3 class="book-name">{{ book.title }}</h3>
                <span class="price">¥{{ book.price }}</span>
              </div>
              <!-- 作者信息 -->
              <p class="book-author">{{ book.author }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧内容区域：排行榜 -->
      <div class="content-right">



      </div>
    </div>
  </div>
</template>

<script setup>
// 导入Vue组合式API
import {ref} from 'vue'
// 导入路由相关
import {useRouter} from 'vue-router'
// 导入Element Plus图标组件
import {ArrowRight} from '@element-plus/icons-vue'
// 导入API函数
import {listBook, selectRecommendBook} from "@/api/mall/book.js";
import {addCart} from "@/api/mall/cart.js";
// 导入Element Plus消息组件
import {ElMessage} from "element-plus";

// 获取基础API地址（从环境变量中读取）
const baseUrl = import.meta.env.VITE_APP_BASE_API

// 初始化路由
const router = useRouter()

/**
 * 查看书籍详情
 * @param {number} bookId - 书籍ID
 */
const viewBookDetail = (bookId) => {
  // 跳转到书籍详情页，传递书籍ID作为参数
  router.push(`/index/bookDetail/${bookId}`)
}

/**
 * 将推荐书籍添加到购物车
 */
const addToCart = () => {
  // 构建购物车项对象
  const item = {
    bookId: recommendBook.value.bookId,  // 书籍ID
    quantity: 1  // 数量默认为1
  }
  // 调用添加购物车API
  addCart(item).then(res => {
    // 成功提示
    ElMessage.success('添加成功~')
  })
}

// 使用ref创建响应式数据

// 推荐书籍数据
const recommendBook = ref({})

// 新书列表数据
const newBookList = ref([])

// 销售数量前5的书籍数据
const topFiveBooks = ref([])

/**
 * 组件挂载后执行的函数
 */
onMounted(() => {
  // 获取推荐书籍
  selectRecommendBook().then(res => {
    recommendBook.value = res.data
  })

  // 获取新书列表（第一页，每页4条）
  listBook({pageNum: 1, pageSize: 4}).then(res => {
    newBookList.value = res.rows
  })
})
</script>

<style scoped>
/* 主容器样式 */
.container {
  background-color: #fdfbf7; /* 浅米色背景，营造温暖舒适的阅读氛围 */
  min-height: 100vh; /* 最小高度为视口高度，确保内容不足时也能铺满屏幕 */
  color: #2c3e50; /* 深蓝灰色文字，易于阅读又不刺眼 */
  padding-bottom: 60px; /* 底部内边距，为内容提供呼吸空间 */
}

/*
 * Hero区域样式（顶部推荐区域）
 * 采用杂志排版风格，注重视觉冲击力
 */
.hero-section {
  max-width: 1200px; /* 最大宽度限制，在大屏幕上保持合适的阅读宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 60px 20px; /* 内边距：上下60px，左右20px */
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  gap: 60px; /* 子元素之间的间距 */
}

/* Hero区域左侧内容容器 */
.hero-content {
  flex: 1; /* 弹性布局：占据剩余空间 */
  padding-right: 20px; /* 右侧内边距，与图片区域保持距离 */
}

/* 推荐标签样式 */
.hero-tag {
  font-size: 14px; /* 较小字号，作为装饰性标签 */
  letter-spacing: 2px; /* 字母间距，增强设计感 */
  color: #c0392b; /* 深红色，吸引注意力 */
  margin-bottom: 15px; /* 底部外边距，与标题保持距离 */
  text-transform: uppercase; /* 转换为大写字母 */
  font-weight: bold; /* 加粗显示 */
}

/* 推荐书籍标题样式 */
.hero-title {
  font-size: 48px; /* 大字号，突出显示 */
  margin: 0 0 20px; /* 外边距：上下0，下边20px */
  line-height: 1.2; /* 行高，确保文字呼吸感 */
  color: #1a1a1a; /* 接近纯黑的深灰色，确保可读性 */
}

/* 推荐书籍描述样式 */
.hero-desc {
  font-size: 16px; /* 正文字号 */
  line-height: 1.8; /* 较大的行高，适合长文本阅读 */
  color: #555; /* 中灰色，比标题颜色浅，形成层次 */
  margin-bottom: 30px; /* 底部外边距，与作者信息区域保持距离 */
  max-width: 450px; /* 最大宽度限制，防止过长的行影响阅读 */
}

/* 作者信息区域样式 */
.hero-meta {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  gap: 20px; /* 子元素之间的间距 */
  margin-bottom: 35px; /* 底部外边距，与按钮区域保持距离 */
}

/* Hero区域右侧图片容器 */
.hero-image-box {
  flex: 1; /* 弹性布局：占据剩余空间 */
  position: relative; /* 相对定位，为装饰元素定位做准备 */
  display: flex; /* 启用弹性布局 */
  justify-content: center; /* 水平居中 */
}

/* Hero区域图片样式 */
.hero-image-box img {
  width: 320px; /* 固定宽度 */
  height: 440px; /* 固定高度 */
  object-fit: cover; /* 保持图片比例，裁剪填充容器 */
  border-radius: 4px; /* 轻微圆角 */
  /* 偏移阴影效果，创造立体感 */
  box-shadow: 20px 20px 0px rgba(0, 0, 0, 0.05);
  z-index: 2; /* 堆叠顺序：在装饰元素之上 */
  transition: transform 0.3s ease; /* 悬停变换动画效果 */
}

/* 图片悬停效果 */
.hero-image-box:hover img {
  transform: translateY(-5px); /* 悬停时向上轻微移动，创造交互感 */
}

/* 装饰性圆形背景 */
.decorative-circle {
  position: absolute; /* 绝对定位，相对于父容器 */
  width: 300px; /* 宽度 */
  height: 300px; /* 高度 */
  background-color: #f0ebe1; /* 浅米色背景 */
  border-radius: 50%; /* 圆形 */
  top: 50%; /* 垂直居中 */
  left: 50%; /* 水平居中 */
  transform: translate(-50%, -50%); /* 精确居中定位 */
  z-index: 1; /* 堆叠顺序：在图片之下 */
}

/*
 * 主要内容区域布局
 * 采用网格布局，左侧主内容区，右侧边栏
 */
.main-layout {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距 */
  display: grid; /* 启用网格布局 */
  grid-template-columns: 1fr 280px; /* 两列：第一列自适应，第二列固定280px */
  gap: 40px; /* 列间距 */
}

/* 区域标题容器样式 */
.section-header {
  display: flex; /* 启用弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: baseline; /* 基线对齐，确保标题和链接在同一水平线 */
  margin-bottom: 30px; /* 底部外边距，与内容区域保持距离 */
}

/* 区域主标题样式 */
.section-header h2 {
  font-size: 28px; /* 较大字号 */
  margin: 0; /* 清除默认外边距 */
}

/* 区域副标题样式 */
.sub-header {
  font-size: 14px; /* 较小字号 */
  color: #8b8b8b; /* 浅灰色，作为辅助信息 */
  font-weight: normal; /* 正常字重，与主标题形成对比 */
  margin-left: 10px; /* 左边距，与主标题保持距离 */
}

/*
 * 书籍网格布局
 * 采用响应式网格，自动适应不同屏幕尺寸
 */
.book-grid {
  display: grid; /* 启用网格布局 */
  /* 响应式列：最小180px，最大1fr，自动填充 */
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 40px 25px; /* 行间距40px，列间距25px */
}

/* 书籍卡片容器样式 */
.aesthetic-card {
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
}

/* 书籍封面包装容器 */
.card-cover-wrapper {
  position: relative; /* 相对定位 */
  border-radius: 4px; /* 轻微圆角 */
  overflow: hidden; /* 溢出隐藏，确保子元素不超出容器 */
  margin-bottom: 12px; /* 底部外边距，与书籍信息保持距离 */
  background: #f0f0f0; /* 浅灰色背景，在图片加载时显示 */
  aspect-ratio: 2 / 3; /* 固定宽高比 2:3，典型的书籍比例 */
}

/* 书籍封面图片样式 */
.card-cover-wrapper img {
  width: 100%; /* 宽度100%填充容器 */
  height: 100%; /* 高度100%填充容器 */
  object-fit: cover; /* 保持图片比例，裁剪填充容器 */
  transition: transform 0.5s ease; /* 缩放变换动画效果 */
}

/* 卡片悬停时图片效果 */
.aesthetic-card:hover .card-cover-wrapper img {
  transform: scale(1.05); /* 轻微放大，创造交互感 */
}

/* 卡片悬停时整体效果 */
.aesthetic-card:hover {
  opacity: 1; /* 完全不透明（这里可能应该是其他效果，因为默认就是1） */
}

/* 书籍信息区域样式 */
.card-info .book-tags span {
  font-size: 10px; /* 非常小的字号，用于标签 */
  color: #999; /* 浅灰色文字 */
  border: 1px solid #eee; /* 浅灰色边框 */
  padding: 1px 4px; /* 内边距 */
  margin-right: 5px; /* 右边距，标签之间的间距 */
  border-radius: 2px; /* 轻微圆角 */
}

/* 书籍名称样式 */
.book-name {
  font-size: 15px; /* 适中字号 */
  margin: 8px 0 4px; /* 外边距：上下8px，下边4px */
  font-weight: 600; /* 中等加粗 */
  line-height: 1.4; /* 合适的行高 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 溢出隐藏 */
  text-overflow: ellipsis; /* 溢出时显示省略号 */
  flex: 1; /* 弹性布局：占据剩余空间 */
}

/* 书籍作者样式 */
.book-author {
  font-size: 12px; /* 较小字号 */
  color: #888; /* 浅灰色文字 */
  margin: 0 0 8px; /* 外边距：上下0，下边8px */
}

/* 标题和价格行布局 */
.title-price-row {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  justify-content: space-between; /* 两端对齐 */
}

/* 价格样式 */
.price {
  font-weight: bold; /* 加粗显示 */
  font-size: 16px; /* 适中字号 */
  margin-left: 10px; /* 左边距，与标题保持距离 */
  flex-shrink: 0; /* 不允许收缩，确保价格不被压缩 */
}

/*
 * 侧边排行榜样式
 */
.ranking-board {
  background: #fff; /* 白色背景 */
  padding: 25px; /* 内边距 */
  border-radius: 8px; /* 圆角 */
}

/* 排行榜标题样式 */
.ranking-board h3 {
  margin-top: 0; /* 清除顶部外边距 */
  margin-bottom: 20px; /* 底部外边距，与列表保持距离 */
  font-size: 18px; /* 适中字号 */
}

/* 排行榜列表样式 */
.ranking-list {
  list-style: none; /* 清除默认列表样式 */
  padding: 0; /* 清除内边距 */
  margin: 0; /* 清除外边距 */
}

/* 排行榜列表项样式 */
.ranking-list li {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  margin-bottom: 20px; /* 底部外边距，列表项之间的间距 */
}

/* 排名序号样式 */
.rank-num {
  font-size: 18px; /* 较大字号 */
  font-weight: bold; /* 加粗显示 */
  color: #ddd; /* 浅灰色，默认颜色 */
  width: 30px; /* 固定宽度，确保对齐 */
}

/* 前3名序号特殊样式 */
.rank-num.top-3 {
  color: #2c3e50; /* 深蓝灰色，突出显示 */
}

/* 排名信息区域样式 */
.rank-info h4 {
  margin: 0 0 4px; /* 外边距：上下0，下边4px */
  font-size: 14px; /* 适中字号 */
  font-weight: normal; /* 正常字重 */
}

/* 购买人数信息样式 */
.rank-info p {
  margin: 0; /* 清除外边距 */
  font-size: 12px; /* 较小字号 */
  color: #999; /* 浅灰色文字 */
}
</style>


<style scoped>
/* 主容器样式 */
.container {
  background-color: #fdfbf7; /* 浅米色背景，营造温暖舒适的阅读氛围 */
  min-height: 100vh; /* 最小高度为视口高度，确保内容不足时也能铺满屏幕 */
  color: #2c3e50; /* 深蓝灰色文字，易于阅读又不刺眼 */
  padding-bottom: 60px; /* 底部内边距，为内容提供呼吸空间 */
}

/*
 * Hero区域样式（顶部推荐区域）
 * 采用杂志排版风格，注重视觉冲击力
 */
.hero-section {
  max-width: 1200px; /* 最大宽度限制，在大屏幕上保持合适的阅读宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 60px 20px; /* 内边距：上下60px，左右20px */
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  gap: 60px; /* 子元素之间的间距 */
}

/* Hero区域左侧内容容器 */
.hero-content {
  flex: 1; /* 弹性布局：占据剩余空间 */
  padding-right: 20px; /* 右侧内边距，与图片区域保持距离 */
}

/* 推荐标签样式 */
.hero-tag {
  font-size: 14px; /* 较小字号，作为装饰性标签 */
  letter-spacing: 2px; /* 字母间距，增强设计感 */
  color: #c0392b; /* 深红色，吸引注意力 */
  margin-bottom: 15px; /* 底部外边距，与标题保持距离 */
  text-transform: uppercase; /* 转换为大写字母 */
  font-weight: bold; /* 加粗显示 */
}

/* 推荐书籍标题样式 */
.hero-title {
  font-size: 48px; /* 大字号，突出显示 */
  margin: 0 0 20px; /* 外边距：上下0，下边20px */
  line-height: 1.2; /* 行高，确保文字呼吸感 */
  color: #1a1a1a; /* 接近纯黑的深灰色，确保可读性 */
}

/* 推荐书籍描述样式 */
.hero-desc {
  font-size: 16px; /* 正文字号 */
  line-height: 1.8; /* 较大的行高，适合长文本阅读 */
  color: #555; /* 中灰色，比标题颜色浅，形成层次 */
  margin-bottom: 30px; /* 底部外边距，与作者信息区域保持距离 */
  max-width: 450px; /* 最大宽度限制，防止过长的行影响阅读 */
}

/* 作者信息区域样式 */
.hero-meta {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  gap: 20px; /* 子元素之间的间距 */
  margin-bottom: 35px; /* 底部外边距，与按钮区域保持距离 */
}

/* Hero区域右侧图片容器 */
.hero-image-box {
  flex: 1; /* 弹性布局：占据剩余空间 */
  position: relative; /* 相对定位，为装饰元素定位做准备 */
  display: flex; /* 启用弹性布局 */
  justify-content: center; /* 水平居中 */
}

/* Hero区域图片样式 */
.hero-image-box img {
  width: 320px; /* 固定宽度 */
  height: 440px; /* 固定高度 */
  object-fit: cover; /* 保持图片比例，裁剪填充容器 */
  border-radius: 4px; /* 轻微圆角 */
  /* 偏移阴影效果，创造立体感 */
  box-shadow: 20px 20px 0px rgba(0, 0, 0, 0.05);
  z-index: 2; /* 堆叠顺序：在装饰元素之上 */
  transition: transform 0.3s ease; /* 悬停变换动画效果 */
}

/* 图片悬停效果 */
.hero-image-box:hover img {
  transform: translateY(-5px); /* 悬停时向上轻微移动，创造交互感 */
}

/* 装饰性圆形背景 */
.decorative-circle {
  position: absolute; /* 绝对定位，相对于父容器 */
  width: 300px; /* 宽度 */
  height: 300px; /* 高度 */
  background-color: #f0ebe1; /* 浅米色背景 */
  border-radius: 50%; /* 圆形 */
  top: 50%; /* 垂直居中 */
  left: 50%; /* 水平居中 */
  transform: translate(-50%, -50%); /* 精确居中定位 */
  z-index: 1; /* 堆叠顺序：在图片之下 */
}

/*
 * 主要内容区域布局
 * 采用网格布局，左侧主内容区，右侧边栏
 */
.main-layout {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距 */
  display: grid; /* 启用网格布局 */
  grid-template-columns: 1fr 280px; /* 两列：第一列自适应，第二列固定280px */
  gap: 40px; /* 列间距 */
}

/* 区域标题容器样式 */
.section-header {
  display: flex; /* 启用弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: baseline; /* 基线对齐，确保标题和链接在同一水平线 */
  margin-bottom: 30px; /* 底部外边距，与内容区域保持距离 */
}

/* 区域主标题样式 */
.section-header h2 {
  font-size: 28px; /* 较大字号 */
  margin: 0; /* 清除默认外边距 */
}

/* 区域副标题样式 */
.sub-header {
  font-size: 14px; /* 较小字号 */
  color: #8b8b8b; /* 浅灰色，作为辅助信息 */
  font-weight: normal; /* 正常字重，与主标题形成对比 */
  margin-left: 10px; /* 左边距，与主标题保持距离 */
}

/*
 * 书籍网格布局
 * 采用响应式网格，自动适应不同屏幕尺寸
 */
.book-grid {
  display: grid; /* 启用网格布局 */
  /* 响应式列：最小180px，最大1fr，自动填充 */
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 40px 25px; /* 行间距40px，列间距25px */
}

/* 书籍卡片容器样式 */
.aesthetic-card {
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
}

/* 书籍封面包装容器 */
.card-cover-wrapper {
  position: relative; /* 相对定位 */
  border-radius: 4px; /* 轻微圆角 */
  overflow: hidden; /* 溢出隐藏，确保子元素不超出容器 */
  margin-bottom: 12px; /* 底部外边距，与书籍信息保持距离 */
  background: #f0f0f0; /* 浅灰色背景，在图片加载时显示 */
  aspect-ratio: 2 / 3; /* 固定宽高比 2:3，典型的书籍比例 */
}

/* 书籍封面图片样式 */
.card-cover-wrapper img {
  width: 100%; /* 宽度100%填充容器 */
  height: 100%; /* 高度100%填充容器 */
  object-fit: cover; /* 保持图片比例，裁剪填充容器 */
  transition: transform 0.5s ease; /* 缩放变换动画效果 */
}

/* 卡片悬停时图片效果 */
.aesthetic-card:hover .card-cover-wrapper img {
  transform: scale(1.05); /* 轻微放大，创造交互感 */
}

/* 卡片悬停时整体效果 */
.aesthetic-card:hover {
  opacity: 1; /* 完全不透明（这里可能应该是其他效果，因为默认就是1） */
}

/* 书籍信息区域样式 */
.card-info .book-tags span {
  font-size: 10px; /* 非常小的字号，用于标签 */
  color: #999; /* 浅灰色文字 */
  border: 1px solid #eee; /* 浅灰色边框 */
  padding: 1px 4px; /* 内边距 */
  margin-right: 5px; /* 右边距，标签之间的间距 */
  border-radius: 2px; /* 轻微圆角 */
}

/* 书籍名称样式 */
.book-name {
  font-size: 15px; /* 适中字号 */
  margin: 8px 0 4px; /* 外边距：上下8px，下边4px */
  font-weight: 600; /* 中等加粗 */
  line-height: 1.4; /* 合适的行高 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 溢出隐藏 */
  text-overflow: ellipsis; /* 溢出时显示省略号 */
  flex: 1; /* 弹性布局：占据剩余空间 */
}

/* 书籍作者样式 */
.book-author {
  font-size: 12px; /* 较小字号 */
  color: #888; /* 浅灰色文字 */
  margin: 0 0 8px; /* 外边距：上下0，下边8px */
}

/* 标题和价格行布局 */
.title-price-row {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  justify-content: space-between; /* 两端对齐 */
}

/* 价格样式 */
.price {
  font-weight: bold; /* 加粗显示 */
  font-size: 16px; /* 适中字号 */
  margin-left: 10px; /* 左边距，与标题保持距离 */
  flex-shrink: 0; /* 不允许收缩，确保价格不被压缩 */
}

/*
 * 侧边排行榜样式
 */
.ranking-board {
  background: #fff; /* 白色背景 */
  padding: 25px; /* 内边距 */
  border-radius: 8px; /* 圆角 */
}

/* 排行榜标题样式 */
.ranking-board h3 {
  margin-top: 0; /* 清除顶部外边距 */
  margin-bottom: 20px; /* 底部外边距，与列表保持距离 */
  font-size: 18px; /* 适中字号 */
}

/* 排行榜列表样式 */
.ranking-list {
  list-style: none; /* 清除默认列表样式 */
  padding: 0; /* 清除内边距 */
  margin: 0; /* 清除外边距 */
}

/* 排行榜列表项样式 */
.ranking-list li {
  display: flex; /* 启用弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  margin-bottom: 20px; /* 底部外边距，列表项之间的间距 */
}

/* 排名序号样式 */
.rank-num {
  font-size: 18px; /* 较大字号 */
  font-weight: bold; /* 加粗显示 */
  color: #ddd; /* 浅灰色，默认颜色 */
  width: 30px; /* 固定宽度，确保对齐 */
}

/* 前3名序号特殊样式 */
.rank-num.top-3 {
  color: #2c3e50; /* 深蓝灰色，突出显示 */
}

/* 排名信息区域样式 */
.rank-info h4 {
  margin: 0 0 4px; /* 外边距：上下0，下边4px */
  font-size: 14px; /* 适中字号 */
  font-weight: normal; /* 正常字重 */
}

/* 购买人数信息样式 */
.rank-info p {
  margin: 0; /* 清除外边距 */
  font-size: 12px; /* 较小字号 */
  color: #999; /* 浅灰色文字 */
}
</style>