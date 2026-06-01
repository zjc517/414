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

    <!-- 顶部按钮 -->
    <el-row :gutter="20" style="padding: 10px 0">
      <el-col :span="1.5">
        <el-button type="primary"
                   plain
                   icon="Plus"
                   @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success"
                   plain
                   icon="Edit"
                   :disabled="single"
                   @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
    </el-row>

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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
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

    <!-- 添加或修改图书分类对话框 -->
    <vxe-modal :title="title" v-model="open" width="30%" show-maximize showFooter resize>
      <el-form ref="categoryRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number style="width: 100%;" v-model="form.sort" placeholder="请输入排序"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </vxe-modal>

  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {addCategory, getCategory, listCategory, updateCategory} from "@/api/mall/category.js";
import Pagination from "@/components/Pagination/index.vue";
import {ElMessage} from "element-plus";

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

//表单校验
const rules = ref({
  name: [
    {required: true, message: '分类名称不能为空', trigger: 'blur'}
  ],
  sort: [
    {required: true, message: '排序不能为空', trigger: 'blur'}
  ],
})

//表单实例
const categoryRef = ref()

//对话框是否打开
const open = ref(false)

//对话框title
const title = ref('')

//表单参数
const form = ref({})

//新增按钮
const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加图书分类'
}

//修改按钮
const handleUpdate = (row) => {
  reset()
  const categoryId = row.categoryId || ids.value
  getCategory(categoryId).then(res => {
    form.value = res.data
    open.value = true
    title.value = '修改图书分类'
  })
}

//表单重置
const reset = () => {
  form.value = {
    categoryId: null,
    name: null,
    sort: null
  }
  if (categoryRef.value) {
    categoryRef.value.resetFields()
  }
}

//取消按钮
const cancel = () => {
  open.value = false
  reset()
}

//提交按钮
const submitForm = () => {
  categoryRef.value.validate(valid => {
    if (valid) {
      if (form.value.categoryId != null) {
        //修改时提交
        updateCategory(form.value).then(res => {
          ElMessage.success('修改成功')
          open.value = false
          getList()
        })
      } else {
        //新增时提交
        addCategory(form.value).then(res => {
          ElMessage.success('提交成功')
          open.value = false
          getList()
        })
      }
    }
  })
}

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
