<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图书名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入图书ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价用户ID" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入评价用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 顶部按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table @row-click="clickRow" ref="tableRef" highlight-current-row
              border v-loading="loading" :data="evaluateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" :index="indexMethod" />
      <el-table-column label="图书名称" align="center" prop="title" />
      <el-table-column label="评分" align="center" prop="rating">
        <template #default="scope">
          <el-rate v-model="scope.row.rating" disabled/>
        </template>
      </el-table-column>
      <el-table-column label="评价内容" align="center" prop="content" />
      <el-table-column label="评价用户" align="center" prop="userName" />
      <el-table-column label="评价时间" align="center" prop="createTime" width="180"/>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改评价对话框 -->
    <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
      <el-form ref="evaluateRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图书ID" prop="bookId">
          <el-input v-model="form.bookId" placeholder="请输入图书ID" />
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <!--<el-input v-model="form.rating" placeholder="请输入评分" /> -->
          <el-rate v-model="form.rating"/>
        </el-form-item>
        <el-form-item label="评价内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </vxe-modal>
  </div>
</template>

<script setup name="Evaluate">
import { listEvaluate, getEvaluate, delEvaluate, addEvaluate, updateEvaluate } from "@/api/mall/evaluate"
import {getToken} from "@/utils/auth.js";
import {ElMessage, ElMessageBox} from "element-plus";
const baseURL = import.meta.env.VITE_APP_BASE_API

const queryRef = ref()
const evaluateRef = ref()
const evaluateList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const selectedRow = ref(null)
const tableRef = ref()

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    bookId: null,
    userId: null,
    title: null,
    userName: null
  },
  rules: {
    bookId: [
      { required: true, message: "图书ID不能为空", trigger: "blur" }
    ],
    rating: [
      { required: true, message: "评分不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "评价内容不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "评价用户ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

//点击行 获取行
const clickRow = (row) => {
  selectedRow.value = row; // 更新选中的行
  const table = tableRef.value;
  // 清除所有已选中的行
  table.clearSelection();
  // 选中当前点击的行
  table.toggleRowSelection(row, true);
}

/** 自定义序号 */
const indexMethod = (index) => {
  let pageNum = queryParams.value.pageNum - 1;
  if((pageNum!==-1 && pageNum!==0)){
    return (index + 1) + (pageNum  * queryParams.value.pageSize);
  }else{
    return (index + 1)
  }
}

/** 查询评价列表 */
const getList = () => {
  loading.value = true
  listEvaluate(queryParams.value).then(response => {
    evaluateList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  form.value = {
    evaluateId: null,
    bookId: null,
    rating: null,
    content: null,
    userId: null,
    createTime: null
  }
  if (evaluateRef.value) {
     evaluateRef.value.resetFields()
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryRef.value.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.evaluateId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加评价"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _evaluateId = row.evaluateId || ids.value
  getEvaluate(_evaluateId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改评价"
  })
}

/** 提交按钮 */
const submitForm = () => {
  evaluateRef.value.validate(valid => {
    if (valid) {
      if (form.value.evaluateId != null) {
        updateEvaluate(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addEvaluate(form.value).then(response => {
          ElMessage.success("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  const _evaluateIds = row.evaluateId || ids.value
  ElMessageBox.confirm('是否确认删除该项数据？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function() {
    return delEvaluate(_evaluateIds)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {})
}

onMounted(() => {
    getList()
})
</script>