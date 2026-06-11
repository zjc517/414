<template>
  <div class="checkout-container">
    <div class="checkout-content">
      <div class="section">
        <div class="section-header">
          <h3>收货地址</h3>
          <el-button type="primary" link @click="handleAdd">
            <el-icon>
              <Plus/>
            </el-icon>
            新增地址
          </el-button>
        </div>
        <!-- 地址列表 -->
        <div class="address-list">
          <div v-for="address in addressList"
               :key="address.addressId"
               class="address-item">
            <el-radio-group v-model="selectedAddressId">
              <el-radio :label="address.addressId">
                <div class="address-info">
                  <div class="address-person">
                    <span class="name">{{ address.name }}</span>
                    <span class="phone">{{ address.phone }}</span>
                  </div>
                  <div class="address">
                    {{ address.detail }}
                  </div>
                </div>
              </el-radio>
            </el-radio-group>
            <div class="address-actions">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(address)">
                修改
              </el-button>
              <el-button link type="danger" icon="Delete" @click="handleDelete(address)">
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 商品信息区域 -->
      <div class="section">
        <div class="section-header">
          <h3>商品信息</h3>
        </div>
        <div class="goods-list">
          <div v-for="item in checkoutItems"
               :key="item.cartId"
               class="goods-item"
          >
            <div class="goods-cover">
              <img :src="baseUrl + item.cover" alt="">
            </div>
            <div class="goods-info">
              <div class="goods-title">{{ item.title }}</div>
              <div class="goods-author">{{ item.author }}</div>
            </div>
            <div class="goods-price">¥{{ item.price }}</div>
            <div class="goods-quantity">× {{ item.quantity }}</div>
            <div class="goods-subtotal">¥{{ (item.price * item.quantity).toFixed(2) }}</div>
          </div>
        </div>
      </div>

      <!-- 提交订单区域 -->
      <div class="checkout-footer">
        <div class="total-amount">
          应付总额: <span class="amount">¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        <el-button type="primary" size="large" @click="submitOrder">
          提交订单
        </el-button>
      </div>

    </div>

    <!-- 添加或修改收货地址对话框 -->
    <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
      <el-form ref="addressRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="收货人" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="收货地址" prop="detail">
          <el-input v-model="form.detail" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="open = false">取 消</el-button>
        </div>
      </template>
    </vxe-modal>

  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {addAddress, delAddress, getAddress, selectMyAddress, updateAddress} from "@/api/mall/address.js";
import {Plus} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {useRoute} from "vue-router";

//基础API地址
const baseUrl = import.meta.env.VITE_APP_BASE_API

//对话框标题
const title = ref('')

//对话框是否打开
const open = ref(false)

//选中的地址ID
const selectedAddressId = ref(null)

//收货地址列表
const addressList = ref([])

//地址表单数据
const form = ref({})

//表单实例
const addressRef = ref()

const route = useRoute()

//计算订单总价
const totalPrice = computed(() => {
  return checkoutItems.value.reduce((total, item) => {
    return total + (item.price * item.quantity)
  }, 0)
})

//获取选中的地址对象
const selectedAddress = computed(() => {
  return addressList.value.find(address => address.addressId === selectedAddressId.value) || null
})

//从路由参数中解析商品数据
const getCheckoutItems = () => {
  return JSON.parse(route.query.products)
}

//提交订单
const submitOrder = () => {
  //验证是否选择了收货地址
  if (!selectedAddress.value) {
    ElMessage.warning('请选择收货地址')
    return
  }
  //构建订单数据
  const item = {
    totalAmount: totalPrice.value.toFixed(2),
    name: selectedAddress.value.name,
    phone: selectedAddress.value.phone,
    address: selectedAddress.value.detail,
    orderBookList: JSON.parse(route.query.products)
  }
  //提取出每一个购物车ID
  const cartIds = JSON.parse(route.query.products)
      .map(item => item.cartId)
      .filter(cartId => cartId !== undefined && cartId !== null)
  addOrder(item).then(res => {
    //只有当cartIds存在并且不为空时才清理购物车数据
    if (cartIds && cartIds.length > 0) {
      //清理对应的购物车数据
      delCart(cartIds).then(response => {
        ElMessage.success('提交成功~')
        //跳转到订单页面 TODO
      })
    } else {
      ElMessage.success('提交成功~')
      //跳转到订单页面 TODO
    }
  })
}


//结算商品列表
const checkoutItems = ref(getCheckoutItems())

// 表单重置
const reset = () => {
  form.value = {
    addressId: null,
    name: null,
    phone: null,
    detail: null,
    userId: null,
    createTime: null
  }
  if (addressRef.value) {
    addressRef.value.resetFields()
  }
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加收货地址"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const addressId = row.addressId
  getAddress(addressId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改收货地址"
  })
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  const addressId = row.addressId
  ElMessageBox.confirm('是否确认删除该项数据？', '系统提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(function () {
    return delAddress(addressId)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {
  })
}

/** 提交按钮 */
const submitForm = () => {
  addressRef.value.validate(valid => {
    if (valid) {
      if (form.value.addressId != null) {
        updateAddress(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAddress(form.value).then(response => {
          ElMessage.success("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

//表单验证
const rules = ref({
  name: [
    {required: true, message: "收货人不能为空", trigger: "blur"}
  ],
  phone: [
    {required: true, message: "联系电话不能为空", trigger: "blur"}
  ],
  detail: [
    {required: true, message: "收货地址不能为空", trigger: "blur"}
  ]
})

//查询数据
const getList = () => {
  selectMyAddress().then(res => {
    addressList.value = res.data
  })
}

onMounted(() => {
  getList()
})

</script>

<style scoped>
/* 容器样式：整个结账页面的容器 */
.checkout-container {
  max-width: 1200px; /* 最大宽度限制，保持在大屏幕上不会过宽 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
  background-color: #fdfbf7; /* 浅米色背景，营造温暖感 */
  min-height: 100vh; /* 最小高度为视口高度，确保内容不足时也能占满屏幕 */
}

/* 主要内容区域样式 */
.checkout-content {
  display: flex; /* 使用弹性布局 */
  flex-direction: column; /* 垂直方向排列子元素 */
  gap: 30px; /* 子元素之间的间距 */
}

/* 区块样式：用于包裹不同功能区域 */
.section {
  background: #fff; /* 白色背景 */
  border-radius: 8px; /* 圆角边框 */
  padding: 25px; /* 内边距 */
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); /* 轻微阴影，增加层次感 */
}

/* 区块头部样式 */
.section-header {
  display: flex; /* 使用弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  margin-bottom: 20px; /* 底部外边距 */
  padding-bottom: 15px; /* 底部内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框，用于分隔标题和内容 */
}

/* 区块标题样式 */
.section-header h3 {
  margin: 0; /* 移除默认外边距 */
  color: #2c3e50; /* 深蓝色文字，提升可读性 */
  font-size: 18px; /* 字体大小 */
}

/* 地址列表容器样式 */
.address-list {
  display: grid; /* 使用网格布局 */
  grid-template-columns: 1fr 1fr; /* 两列等宽布局 */
  gap: 15px; /* 网格间距 */
}

/* 单个地址项样式 */
.address-item {
  padding: 15px; /* 内边距 */
  border: 1px solid #f0f0f0; /* 浅灰色边框 */
  border-radius: 4px; /* 圆角 */
  display: flex; /* 使用弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  box-sizing: border-box; /* 盒模型计算方式 */
  width: 100%; /* 宽度100% */
  min-width: 0; /* 最小宽度为0，防止内容溢出 */
}

/* 地址项悬停效果 */
.address-item:hover {
  border-color: #409eff; /* 悬停时边框变为蓝色 */
}

/* 地址信息区域样式 */
.address-info {
  margin-left: 10px; /* 左侧外边距 */
  flex-grow: 1; /* 允许扩展填充剩余空间 */
  min-width: 0; /* 最小宽度为0，防止文本溢出 */
}

/* 收货人信息样式 */
.address-person {
  margin-bottom: 8px; /* 底部外边距 */
}

/* 收货人姓名样式 */
.address-person .name {
  font-weight: bold; /* 粗体 */
  margin-right: 15px; /* 右侧外边距，与手机号分隔 */
}

/* 地址文本样式 */
.address {
  color: #666; /* 深灰色文字 */
  font-size: 14px; /* 稍小的字体 */
  white-space: nowrap; /* 禁止换行 */
  overflow: hidden; /* 隐藏溢出内容 */
  text-overflow: ellipsis; /* 超出部分显示省略号 */
}

/* 地址操作按钮区域样式 */
.address-actions {
  display: flex; /* 使用弹性布局 */
  gap: 10px; /* 按钮间距 */
}

/* 商品列表容器样式 */
.goods-list {
  display: flex; /* 使用弹性布局 */
  flex-direction: column; /* 垂直方向排列 */
  gap: 20px; /* 商品项之间的间距 */
}

/* 单个商品项样式 */
.goods-item {
  display: flex; /* 使用弹性布局 */
  align-items: center; /* 垂直居中 */
  gap: 20px; /* 子元素之间的间距 */
  padding: 15px 0; /* 上下内边距 */
  border-bottom: 1px solid #f0f0f0; /* 底部边框，分隔商品 */
}

/* 最后一个商品项去除底部边框 */
.goods-item:last-child {
  border-bottom: none;
}

/* 商品封面容器样式 */
.goods-cover {
  width: 60px; /* 固定宽度 */
  height: 80px; /* 固定高度，适合书籍封面比例 */
  border-radius: 4px; /* 圆角 */
  overflow: hidden; /* 隐藏溢出部分 */
  background: #f5f5f5; /* 浅灰色背景，图片加载时显示 */
  display: flex; /* 使用弹性布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

/* 商品封面图片样式 */
.goods-cover img {
  max-width: 100%; /* 最大宽度100%，防止超出容器 */
  max-height: 100%; /* 最大高度100%，保持比例 */
  object-fit: contain; /* 保持图片比例，完整显示 */
}

/* 商品信息区域样式 */
.goods-info {
  flex: 1; /* 占据剩余空间 */
}

/* 商品标题样式 */
.goods-title {
  font-weight: 600; /* 稍粗的字体 */
  margin-bottom: 5px; /* 底部外边距 */
  color: #2c3e50; /* 深蓝色文字 */
}

/* 商品作者样式 */
.goods-author {
  font-size: 13px; /* 较小字体 */
  color: #7f8c8d; /* 灰色文字 */
}

/* 商品单价样式 */
.goods-price {
  width: 100px; /* 固定宽度 */
  color: #2c3e50; /* 深蓝色文字 */
}

/* 商品数量样式 */
.goods-quantity {
  width: 50px; /* 固定宽度 */
  text-align: center; /* 居中显示 */
  color: #666; /* 灰色文字 */
}

/* 商品小计样式 */
.goods-subtotal {
  width: 100px; /* 固定宽度 */
  text-align: right; /* 右对齐 */
  font-weight: bold; /* 粗体 */
  color: #e74c3c; /* 红色，强调金额 */
}

/* 结账底部区域样式 */
.checkout-footer {
  position: sticky; /* 粘性定位 */
  bottom: 0; /* 固定在底部 */
  padding: 20px 0; /* 上下内边距 */
  display: flex; /* 使用弹性布局 */
  justify-content: flex-end; /* 右对齐 */
  align-items: center; /* 垂直居中 */
  gap: 20px; /* 子元素之间的间距 */
  background: #fdfbf7; /* 与背景色一致 */
  z-index: 10; /* 确保在最上层 */
}

/* 总金额样式 */
.total-amount {
  font-size: 16px; /* 字体大小 */
  font-weight: bold; /* 粗体 */
}

/* 金额数字样式 */
.amount {
  color: #e74c3c; /* 红色，强调金额 */
  font-size: 20px; /* 较大的字体 */
}
</style>
