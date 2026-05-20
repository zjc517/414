<template>
  <div>
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="70px">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="queryParams.name"
                  placeholder="请输入分类名称"
                  clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table :data="categoryList" ref="tableRef" highlight-current-row
              border @row-click="clickRow"
              v-loading="loading" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" width="55" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="分类名称" prop="name" align="center"/>
      <el-table-column label="排序" prop="sort" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <Pagination :total="total"
                v-model:page="queryParams.pageNum"
                v-model:limit="queryParams.pageSize"
                @pagination="getList"
    />

  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {getCategory, listCategory} from "@/api/mall/category.js";
import Pagination from "@/components/Pagination/index.vue";

//搜索表单实例
const queryRef = ref()

//加载状态
const loading = ref(false)

//当前选中的行的ID数组
const ids = ref([])

//当前是否未选中单行
const single = ref(true)

//当前是否未选中多行
const multiple = ref(true)

//当前选中的行
const selectedRow = ref(null)

//表格实例
const tableRef = ref()

//点击行后获取行的信息
const clickRow = (row) => {
  selectedRow.value = row
  const table = tableRef.value
  //清除已选中的行
  table.clearSelection()
  //选中当前点击的行
  table.toggleRowSelection(row, true)
}

//多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.categoryId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

//自定义序号
const indexMethod = (index) => {
  let pageNum = queryParams.value.pageNum - 1;
  if ((pageNum !== -1 && pageNum !== 0)) {
    return (index + 1) + (pageNum * queryParams.value.pageSize);
  } else {
    return (index + 1)
  }
}

//查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  name: null
})

//列表数据
const categoryList = ref([])

//数据总数
const total = ref(0)

//查询
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

//重置
const resetQuery = () => {
  queryRef.value.resetFields()
  handleQuery()
}

//查询数据
const getList = () => {
  loading.value = true
  listCategory(queryParams.value).then(res => {
    total.value = res.total
    categoryList.value = res.rows
    loading.value = false
  })
}

//组件挂载完成后执行
//组件挂载完成后执行
onMounted(() => {
  getList()


})
</script>

<style scoped>

</style>
