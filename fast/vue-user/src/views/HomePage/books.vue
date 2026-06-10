<template>
  <div class="books-container">
    <!-- 筛选区域 -->
    <div class="filter-section">
      <div class="filter-bar">
        <el-input v-model="query.title"
                  placeholder="搜索图书名称"
                  clearable
                  @clear="handleQuery"
                  style="margin-right: 20px;width: 300px;"
        >
          <template #append>
            <el-button icon="Search" @click="handleQuery"/>
          </template>
        </el-input>

        <el-select style="width: 200px;margin-right: 20px"
                   v-model="query.categoryId"
                   placeholder="全部分类"
                   @change="handleQuery"
                   clearable
                   @clear="handleQuery"
        >
          <el-option v-for="category in categoryList"
                     :key="category.categoryId"
                     :label="category.name"
                     :value="category.categoryId"
          />
        </el-select>

      </div>
    </div>

    <!-- 图书列表展示 -->
    <div class="books-content">
      <div class="book-grid">
        <div v-for="book in bookList"
             :key="book.bookId"
             class="book-card"
             @click="toGoDetail(book.bookId)"
        >
          <div class="book-cover">
            <img :src="baseUrl + book.cover" alt="">
          </div>
          <div class="book-info">
            <h3 class="book-title">{{ book.title }}</h3>
            <div style="display: flex; justify-content: space-between; align-items: center">
              <p class="book-author">{{ book.author }}</p>
              <div class="book-price">
                <span class="current-price">¥{{ book.price }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 分页组件 -->
      <div class="pagination-container">
        <pagination
            v-show="total>0"
            :total="total"
            v-model:page="query.pageNum"
            v-model:limit="query.pageSize"
            @pagination="getList"
            :page-sizes="[8,16,32]"
        />
      </div>

    </div>

  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {listBook} from "@/api/mall/book.js";
import {selectAllCategory} from "@/api/mall/category.js";
import {useRouter} from "vue-router";

//基础API地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//查询参数
const query = ref({
  pageNum: 1,
  pageSize: 8,
  categoryId: null,
  title: null
})

/** 搜索按钮操作 */
const handleQuery = () => {
  query.value.pageNum = 1
  getList()
}

//图书列表数据
const bookList = ref([])

//数据总数
const total = ref(0)

//路由实例
const router = useRouter()

//跳转到图书详情页面
const toGoDetail = (bookId) => {
  router.push(`/index/bookDetail/${bookId}`)
}

//查询数据
const getList = () => {
  listBook(query.value).then(res => {
    bookList.value = res.rows
    console.log(bookList.value, '看看数据')
    total.value = res.total
  })
}

//分类列表数据
const categoryList = ref([])

onMounted(() => {
  getList()

  //查询所有分类
  selectAllCategory().then(res => {
    categoryList.value = res.data
  })
})

</script>

<style scoped>
/*
 * 图书列表容器
 * 设置最大宽度、居中、内边距和背景色
 * min-height确保内容区域最小高度为视口高度
 */
.books-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
  background-color: #fdfbf7; /* 淡黄色背景，营造书籍氛围 */
  min-height: 100vh; /* 最小高度为视口高度 */
}

/* 筛选区域样式 */
.filter-section {
  margin-bottom: 30px; /* 底部间距 */
  padding: 20px; /* 内边距 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 筛选栏布局 */
.filter-bar {
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  flex-wrap: wrap; /* 允许换行 */
  gap: 10px; /* 元素间距 */
}

/* 图书内容区域 */
.books-content {
  border-radius: 8px; /* 圆角 */
  padding: 20px; /* 内边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 图书网格布局 */
.book-grid {
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); /* 自适应列宽，最小220px */
  gap: 30px; /* 网格间隙 */
  margin-bottom: 30px; /* 底部间距 */
}

/* 图书卡片样式 */
.book-card {
  cursor: pointer; /* 鼠标悬停手型 */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 过渡动画 */
  border-radius: 8px; /* 圆角 */
  overflow: hidden; /* 内容溢出隐藏 */
  background: #fafafa; /* 卡片背景色 */
}

/* 图书卡片悬停效果 */
.book-card:hover {
  transform: translateY(-5px); /* 向上移动5px */
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); /* 增强阴影 */
}

/* 图书封面容器 */
.book-cover {
  width: 100%; /* 宽度100% */
  height: 260px; /* 固定高度 */
  overflow: hidden; /* 溢出隐藏 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  background: #f5f5f5; /* 封面背景色 */
}

/* 图书封面图片 */
.book-cover img {
  max-width: 100%; /* 最大宽度100% */
  max-height: 100%; /* 最大高度100% */
  object-fit: contain; /* 保持比例，完整显示 */
}

/* 图书信息区域 */
.book-info {
  padding: 15px; /* 内边距 */
}

/* 图书标题样式 */
.book-title {
  font-size: 16px; /* 字体大小 */
  font-weight: 600; /* 字体粗细 */
  margin: 0 0 8px 0; /* 外边距：上右下左 */
  color: #2c3e50; /* 深蓝色文字 */
  display: -webkit-box; /* 弹性盒子布局（兼容旧版） */
  -webkit-line-clamp: 2; /* 限制显示2行 */
  -webkit-box-orient: vertical; /* 垂直方向排列 */
  overflow: hidden; /* 溢出隐藏 */
}

/* 图书作者样式 */
.book-author {
  font-size: 13px; /* 较小字体 */
  color: #7f8c8d; /* 灰色文字 */
  margin: 0; /* 无外边距 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 溢出隐藏 */
  text-overflow: ellipsis; /* 溢出显示省略号 */
}

/* 图书价格区域 */
.book-price {
  margin-left: 10px; /* 左外边距 */
  flex-shrink: 0; /* 禁止收缩 */
}

/* 当前价格样式 */
.current-price {
  font-size: 18px; /* 较大字体 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色价格 */
  margin-right: 8px; /* 右外边距 */
}

/* 分页容器 */
.pagination-container {
  display: flex; /* 弹性布局 */
  justify-content: center; /* 水平居中 */
  padding: 20px 0; /* 上下内边距 */
}
</style>
