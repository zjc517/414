<template>
  <div class="recharge-container">
    <div class="recharge-header">
      <p>当前余额: <span class="balance">¥{{ myBalance }}</span></p>
    </div>

    <div class="recharge-content">
      <div class="recharge-card">
        <h3>充值金额</h3>
        <div class="amount-input">
          <span class="currency">¥</span>
          <el-input v-model="rechargeAmount"
                    placeholder="请输入充值金额"
                    type="number"
                    @input="handleAmountInput"
          />
        </div>

        <div class="preset-amounts">
          <el-button v-for="amount in presetAmounts"
                     :key="amount"
                     :class="{ active: rechargeAmount === amount }"
                     @click="selectPresetAmounts(amount)"
          >
            ¥{{ amount }}
          </el-button>
        </div>
      </div>

      <div class="recharge-card">
        <h3>充值方式</h3>
        <div class="payment-methods">
          <div v-for="method in paymentMethods"
               :key="method.key"
               :class="['method-item', { active: selectedMethod === method.key }]"
               @click="selectPaymentMethod(method.key)"
          >
            <div class="method-icon">
              <SvgIcon icon-class="微信" v-if="method.key === 'wechat'"/>
              <SvgIcon icon-class="支付宝" v-if="method.key === 'alipay'"/>
              <SvgIcon icon-class="银行卡" v-if="method.key === 'bank'"/>
            </div>
            <div class="method-info">
              <div class="method-name">{{ method.name }}</div>
              <div class="method-desc">{{ method.desc }}</div>
            </div>
            <div class="method-check">
              <el-icon v-if="selectedMethod === method.key">
                <Check/>
              </el-icon>
            </div>
          </div>
        </div>
      </div>

      <div class="recharge-action">
        <el-button type="primary"
                   size="large"
                   :disabled="!canRecharge"
                   @click="confirmRecharge()"
        >
          立即充值
        </el-button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { selectMyBalance } from "@/api/system/user.js"
import request from '@/utils/request'
import { recharge } from "@/api/system/user.js"  // 需要创建这个API
import SvgIcon from "@/components/SvgIcon/index.vue"
import { Check } from "@element-plus/icons-vue"

// 预设金额数组
const presetAmounts = [100, 200, 500, 1000, 5000]

// 充值金额
const rechargeAmount = ref(null)

// 账户余额
const myBalance = ref(0)

// 选中的支付方式
const selectedMethod = ref('wechat')

// 支付方式数组
const paymentMethods = [
  {
    key: 'wechat',
    name: '微信支付',
    desc: '推荐使用微信支付',
  },
  {
    key: 'alipay',
    name: '支付宝支付',
    desc: '支持花呗分期',
  },
  {
    key: 'bank',
    name: '银行卡支付',
    desc: '支持储蓄卡和信用卡',
  }
]

const confirmRecharge = () => {
  //如果不符合充值条件, 显示警告
  if (!canRecharge.value) {
    ElMessage.warning('请输入有效的充值金额并选择支付条件')
    return
  }
  //检查充值金额是否小于最小金额
  if (parseFloat(rechargeAmount.value) < 0.01) {
    ElMessage.warning('充值金额不能小于0.01元')
    return;
  }
  //调用充值接口
  recharge({
    amount: rechargeAmount.value,
    method: selectedMethod.value
  }).then(res => {
    if (res.code === 200) {
      ElMessage.success(`充值成功! 当前余额: ¥${myBalance.value + parseFloat(rechargeAmount.value)}`)
      getMyBalance()
      rechargeAmount.value = null
    } else {
      ElMessage.error(res.msg || '充值失败')
    }
  }).catch(error => {
    console.error('充值失败:', error)
    ElMessage.error('充值失败，请稍后重试')
  })
}

//查询余额
const getMyBalance = () => {
  selectMyBalance().then(res => {
    myBalance.value = res.data
  })
}

onMounted(() => {
  getMyBalance()
})

// 判断是否可以充值 条件: 充值金额必须存在且大于0, 并且已选择了支付方式
const canRecharge = computed(() => {
  return rechargeAmount.value && rechargeAmount.value > 0 && selectedMethod.value
})

// 选择金额
const selectPresetAmounts = (amount) => {
  rechargeAmount.value = amount
}

// 处理金额输入, 确保输入的是正数
const handleAmountInput = (value) => {
  if (value && parseFloat(value) < 0) {
    rechargeAmount.value = null
  }
}

// 选择支付方式
const selectPaymentMethod = (method) => {
  selectedMethod.value = method
}

// 处理充值
const handleRecharge = async () => {
  if (!canRecharge.value) {
    ElMessage.warning('请填写充值金额并选择支付方式')
    return
  }

  try {
    // 调用充值接口
    const res = await recharge({
      amount: rechargeAmount.value,
      method: selectedMethod.value
    })

    if (res.code === 200) {
      ElMessage.success(`成功充值 ¥${rechargeAmount.value}`)
      // 刷新余额
      await fetchBalance()
      // 清空充值金额
      rechargeAmount.value = null
    } else {
      ElMessage.error(res.msg || '充值失败')
    }
  } catch (error) {
    console.error('充值失败:', error)
    ElMessage.error('充值失败，请稍后重试')
  }
}

// 获取余额
const fetchBalance = async () => {
  try {
    const res = await selectMyBalance()
    myBalance.value = res.data || 0
  } catch (error) {
    console.error('获取余额失败:', error)
    myBalance.value = 0
  }
}

onMounted(() => {
  fetchBalance()
})
</script>

<style scoped>
.recharge-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fdfbf7;
  min-height: 100vh;
}

.recharge-header {
  margin-bottom: 30px;
  text-align: center;
}

.balance {
  font-size: 20px;
  font-weight: bold;
  color: #e74c3c;
}

.recharge-content {
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.recharge-card {
  background: #fff;
  border-radius: 8px;
  padding: 25px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.recharge-card h3 {
  margin: 0 0 20px 0;
  color: #2c3e50;
  font-size: 18px;
}

.amount-input {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.currency {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  margin-right: 10px;
}

.amount-input :deep(.el-input__wrapper) {
  flex: 1;
  padding: 15px;
}

.preset-amounts {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.preset-amounts .el-button {
  padding: 12px 20px;
  border-color: #dcdfe6;
  color: #606266;
}

.preset-amounts .el-button.active {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
}

.payment-methods {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.method-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;
}

.method-item:hover,
.method-item.active {
  border-color: #409eff;
  box-shadow: 0 0 5px rgba(64, 158, 255, 0.2);
}

.method-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #ecf5ff;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  color: #409eff;
  font-size: 30px;
}

.method-info {
  flex: 1;
}

.method-name {
  font-weight: 500;
  color: #2c3e50;
  margin-bottom: 3px;
}

.method-desc {
  font-size: 12px;
  color: #909399;
}

.method-check {
  width: 20px;
  height: 20px;
  border: 1px solid #dcdfe6;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #409eff;
}

.method-item.active .method-check {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
}

.recharge-action {
  text-align: center;
}

.recharge-action .el-button {
  width: 200px;
  padding: 15px;
}
</style>