<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select style="width: 200px;" v-model="queryParams.status" placeholder="请选择订单状态">
          <el-option label="待付款" value="待付款"/>
          <el-option label="待发货" value="待发货"/>
          <el-option label="已发货" value="已发货"/>
          <el-option label="已完成" value="已完成"/>
          <el-option label="已取消" value="已取消"/>
        </el-select>
      </el-form-item>
      <el-form-item label="下单用户" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入下单用户"
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
              border v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" :index="indexMethod" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="订单总价" align="center" prop="totalAmount" />
      <el-table-column label="收货人" align="center" prop="name" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="收货地址" align="center" prop="address" />
      <el-table-column label="订单状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="warning" v-if="scope.row.status === '待付款'">{{ scope.row.status }}</el-tag>
          <el-tag type="info" v-if="scope.row.status === '待发货'">{{ scope.row.status }}</el-tag>
          <el-tag type="primary" v-if="scope.row.status === '已发货'">{{ scope.row.status }}</el-tag>
          <el-tag type="success" v-if="scope.row.status === '已完成'">{{ scope.row.status }}</el-tag>
          <el-tag type="danger" v-if="scope.row.status === '已取消'">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下单用户ID" align="center" prop="userName" />
      <el-table-column label="订单创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
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

    <!-- 添加或修改订单对话框 -->
    <vxe-modal :title="title" v-model="open" width="50%" show-maximize showFooter resize>
      <el-form ref="orderRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单总价" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入订单总价" />
        </el-form-item>
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="收货地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">订单图书信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddOrderBook">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteOrderBook">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="orderBookList" :row-class-name="rowOrderBookIndex" height ="30vh"
                  @selection-change="handleOrderBookSelectionChange" ref="orderBook">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="图书ID" prop="bookId" >
            <template #default="scope">
              <el-input v-model="scope.row.bookId" placeholder="请输入图书ID" />
            </template>
          </el-table-column>
          <el-table-column label="图书名称" prop="title" >
            <template #default="scope">
              <el-input v-model="scope.row.title" placeholder="请输入图书名称" />
            </template>
          </el-table-column>
          <el-table-column label="作者" prop="author" >
            <template #default="scope">
              <el-input v-model="scope.row.author" placeholder="请输入作者" />
            </template>
          </el-table-column>
          <el-table-column label="价格" prop="price">
            <template #default="scope">
              <el-input v-model="scope.row.price" placeholder="请输入价格" />
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity">
            <template #default="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入数量" />
            </template>
          </el-table-column>
        </el-table>
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

<script setup name="Order">
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/mall/order"
import {getToken} from "@/utils/auth.js";
import {ElMessage, ElMessageBox} from "element-plus";
const baseURL = import.meta.env.VITE_APP_BASE_API

const queryRef = ref()
const orderRef = ref()
const orderList = ref([])
const orderBookList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedOrderBook = ref([])
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
    orderId: null,
    status: null,
    userId: null,
    userName: null
  },
  rules: {
    totalAmount: [
      { required: true, message: "订单总价不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "收货人不能为空", trigger: "blur" }
    ],
    phone: [
      { required: true, message: "联系电话不能为空", trigger: "blur" }
    ],
    address: [
      { required: true, message: "收货地址不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "订单状态不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "下单用户ID不能为空", trigger: "blur" }
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

/** 查询订单列表 */
const getList = () => {
  loading.value = true
  listOrder(queryParams.value).then(response => {
    orderList.value = response.rows
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
    orderId: null,
    totalAmount: null,
    name: null,
    phone: null,
    address: null,
    status: null,
    userId: null,
    createTime: null
  }
  orderBookList.value = []
  if (orderRef.value) {
     orderRef.value.resetFields()
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
  ids.value = selection.map(item => item.orderId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加订单"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _orderId = row.orderId || ids.value
  getOrder(_orderId).then(response => {
    form.value = response.data
    orderBookList.value = response.data.orderBookList
    open.value = true
    title.value = "修改订单"
  })
}

/** 提交按钮 */
const submitForm = () => {
  orderRef.value.validate(valid => {
    if (valid) {
      form.value.orderBookList = orderBookList.value
      if (form.value.orderId != null) {
        updateOrder(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addOrder(form.value).then(response => {
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
  const _orderIds = row.orderId || ids.value
  ElMessageBox.confirm('是否确认删除该项数据？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function() {
    return delOrder(_orderIds)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {})
}

/** 订单图书序号 */
const rowOrderBookIndex = ({ row, rowIndex }) => {
  row.index = rowIndex + 1
}

/** 订单图书添加按钮操作 */
const handleAddOrderBook = () => {
  let obj = {}
  obj.bookId = ""
  obj.title = ""
  obj.author = ""
  obj.cover = ""
  obj.price = ""
  obj.quantity = ""
  orderBookList.value.push(obj)
}

/** 订单图书删除按钮操作 */
const handleDeleteOrderBook = () => {
  if (checkedOrderBook.value.length == 0) {
    ElMessage.error("请先选择要删除的订单图书数据")
  } else {
    const orderBooks = orderBookList.value
    const checkedOrderBooks = checkedOrderBook.value
    orderBookList.value = orderBooks.filter(function(item) {
      return checkedOrderBooks.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
const handleOrderBookSelectionChange = (selection) => {
  checkedOrderBook.value = selection.map(item => item.index)
}

onMounted(() => {
    getList()
})
</script>