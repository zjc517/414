<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="图书分类" prop="categoryId">
          <el-select style="width: 200px;" v-model="queryParams.categoryId" placeholder="请选择图书分类">
            <el-option v-for="category in categoryList"
                       :key="category.categoryId"
                       :label="category.name"
                       :value="category.categoryId"
            />
          </el-select>
      </el-form-item>
      <el-form-item label="书名" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入书名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
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
              border v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" :index="indexMethod" />
      <el-table-column label="图书分类" align="center" prop="name" />
      <el-table-column label="书名" align="center" prop="title" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="封面" align="center" prop="cover" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.cover" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="上架时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="推荐" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button v-if="!scope.row.isRecommend" type="primary" @click="recommendBook(scope.row)">
            图书推荐
          </el-button>
        </template>
      </el-table-column>
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

    <!-- 添加或修改图书对话框 -->
    <vxe-modal :title="title" v-model="open" width="43%" show-maximize showFooter resize>
      <el-form ref="bookRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="书名" prop="title">
              <el-input v-model="form.title" placeholder="请输入书名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图书分类" prop="categoryId">
              <!--<el-input v-model="form.categoryId" placeholder="请输入分类ID"/>-->
              <el-select v-model="form.categoryId" placeholder="请选择图书分类">
                <el-option v-for="category in categoryList"
                           :key="category.categoryId"
                           :label="category.name"
                           :value="category.categoryId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="作者" prop="author">
              <el-input v-model="form.author" placeholder="请输入作者"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格" prop="price">
              <el-input-number style="width: 100%;" v-model="form.price" placeholder="请输入价格"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="封面" prop="cover">
              <image-upload v-model="form.cover"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述" prop="description">
              <el-input :rows="7" v-model="form.description" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
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

<script setup name="Book">
import { listBook, getBook, delBook, addBook, updateBook, recommend } from "@/api/mall/book"
import {getToken} from "@/utils/auth.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {selectAllCategory} from "@/api/mall/category.js";
const baseURL = import.meta.env.VITE_APP_BASE_API

const queryRef = ref()
const bookRef = ref()
const bookList = ref([])
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
    categoryId: null,
    title: null,
    author: null,
  },
  rules: {
    categoryId: [
      { required: true, message: "分类ID不能为空", trigger: "blur" }
    ],
    title: [
      { required: true, message: "书名不能为空", trigger: "blur" }
    ],
    author: [
      { required: true, message: "作者不能为空", trigger: "blur" }
    ],
    cover: [
      { required: true, message: "封面不能为空", trigger: "blur" }
    ],
    price: [
      { required: true, message: "价格不能为空", trigger: "blur" }
    ],
    description: [
      { required: true, message: "描述不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

//图书推荐
const recommendBook = (row) => {
  recommend(row.bookId).then(res => {
    getList()
    ElMessage.success('推荐成功~')
  })
}

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

/** 查询图书列表 */
const getList = () => {
  loading.value = true
  listBook(queryParams.value).then(response => {
    bookList.value = response.rows
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
    bookId: null,
    categoryId: null,
    title: null,
    author: null,
    cover: null,
    price: null,
    description: null,
    createTime: null
  }
  if (bookRef.value) {
     bookRef.value.resetFields()
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
  ids.value = selection.map(item => item.bookId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加图书"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _bookId = row.bookId || ids.value
  getBook(_bookId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改图书"
  })
}

/** 提交按钮 */
const submitForm = () => {
  bookRef.value.validate(valid => {
    if (valid) {
      if (form.value.bookId != null) {
        updateBook(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addBook(form.value).then(response => {
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
  const _bookIds = row.bookId || ids.value
  ElMessageBox.confirm('是否确认删除该项数据？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function() {
    return delBook(_bookIds)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {})
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