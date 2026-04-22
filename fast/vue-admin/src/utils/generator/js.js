import { titleCase } from '@/utils/index'
import { trigger } from './config'
// 文件大小设置
const units = {
  KB: '1024',
  MB: '1024 / 1024',
  GB: '1024 / 1024 / 1024',
}

/**
 * @name: 生成参数
 * @description: 生成参数，包括表单数据表单验证数据，多选选项数据，上传数据等
 * @return {*}
 */
function buildAttributes(
  el,
  dataList,
  ruleList,
  optionsList,
  methodList,
  propsList,
  uploadVarList
){
  buildData(el, dataList)
  buildRules(el, ruleList)

  if (el.options && el.options.length) {
    buildOptions(el, optionsList)
    if (el.dataType === 'dynamic') {
      const model = `${el.vModel}Options`
      const options = titleCase(model)
      buildOptionMethod(`get${options}`, model, methodList)
    }
  }

  if (el.props && el.props.props) {
    buildProps(el, propsList)
  }

  if (el.action && el.tag === 'el-upload') {
    uploadVarList.push(
      `
      // 上传请求路径
      const ${el.vModel}Action = ref('${el.action}')
      // 上传文件列表
      const ${el.vModel}fileList =  ref([])`
    )
    methodList.push(buildBeforeUpload(el))
    if (!el['auto-upload']) {
      methodList.push(buildSubmitUpload(el))
    }
  }

  if (el.children) {
    el.children.forEach((el2) => {
      buildAttributes(
        el2,
        dataList,
        ruleList,
        optionsList,
        methodList,
        propsList,
        uploadVarList
      )
    })
  }
}
/**
 * @name: 生成表单数据formData
 * @description: 生成表单数据formData
 * @param {*} conf
 * @param {*} dataList 数据列表
 * @return {*}
 */
function buildData(conf, dataList) {
  if (conf.vModel === undefined) return
  let defaultValue
  if (typeof conf.defaultValue === 'string' && !conf.multiple) {
    defaultValue = `'${conf.defaultValue}'`
  } else {
    defaultValue = `${JSON.stringify(conf.defaultValue)}`
  }
  dataList.push(`${conf.vModel}: ${defaultValue},`)
}
/**
 * @name: 生成表单验证数据rule
 * @description: 生成表单验证数据rule
 * @param {*} conf
 * @param {*} ruleList 验证数据列表
 * @return {*}
 */
function buildRules(conf, ruleList) {
  if (conf.vModel === undefined) return
  const rules = []
  if (trigger[conf.tag]) {
    if (conf.required) {
      const type = Array.isArray(conf.defaultValue) ? "type: 'array'," : ''
      let message = Array.isArray(conf.defaultValue)
        ? `请至少选择一个${conf.vModel}`
        : conf.placeholder
      if (message === undefined) message = `${conf.label}不能为空`
      rules.push(
        `{ required: true, ${type} message: '${message}', trigger: '${
          trigger[conf.tag]
        }' }`
      )
    }
    if (conf.regList && Array.isArray(conf.regList)) {
      conf.regList.forEach((item) => {
        if (item.pattern) {
          rules.push(
            `{ pattern: new RegExp(${item.pattern}), message: '${
              item.message
            }', trigger: '${trigger[conf.tag]}' }`
          )
        }
      })
    }
    ruleList.push(`${conf.vModel}: [${rules.join(',')}],`)
  }
}
/**
 * @name: 生成选项数据
 * @description: 生成选项数据，单选多选下拉等
 * @param {*} conf
 * @param {*} optionsList 选项数据列表
 * @return {*}
 */
function buildOptions(conf, optionsList) {
  if (conf.vModel === undefined) return
  if (conf.dataType === 'dynamic') {
    conf.options = []
  }
  const str = `const ${conf.vModel}Options = ref(${JSON.stringify(conf.options)})`
  optionsList.push(str)
}
/**
 * @name: 生成方法
 * @description: 生成方法
 * @param {*} methodName 方法名
 * @param {*} model
 * @param {*} methodList 方法列表
 * @return {*}
 */
function buildOptionMethod(methodName, model, methodList) {
  const str = `function ${methodName}() {
    // TODO 发起请求获取数据
    ${model}.value
  }`
  methodList.push(str)
}
/**
 * @name: 生成表单组件需要的props设置
 * @description: 生成表单组件需要的props设置，如；级联组件
 * @param {*} conf
 * @param {*} propsList
 * @return {*}
 */
function buildProps(conf, propsList) {
  if (conf.dataType === 'dynamic') {
    conf.valueKey !== 'value' && (conf.props.props.value = conf.valueKey)
    conf.labelKey !== 'label' && (conf.props.props.label = conf.labelKey)
    conf.childrenKey !== 'children' &&
      (conf.props.props.children = conf.childrenKey)
  }
  const str = `
  // props设置
  const ${conf.vModel}Props = ref(${JSON.stringify(conf.props.props)})`
  propsList.push(str)
}
/**
 * @name: 生成上传组件的相关内容
 * @description: 生成上传组件的相关内容
 * @param {*} conf
 * @return {*}
 */
function buildBeforeUpload(conf) {
  const unitNum = units[conf.sizeUnit]
  let rightSizeCode = ''
  let acceptCode = ''
  const returnList = []
  if (conf.fileSize) {
    rightSizeCode = `let isRightSize = file.size / ${unitNum} < ${conf.fileSize}
    if(!isRightSize){
      proxy.$modal.msgError('文件大小超过 ${conf.fileSize}${conf.sizeUnit}')
    }`
    returnList.push('isRightSize')
  }
  if (conf.accept) {
    acceptCode = `let isAccept = new RegExp('${conf.accept}').test(file.type)
    if(!isAccept){
      proxy.$modal.msgError('应该选择${conf.accept}类型的文件')
    }`
    returnList.push('isAccept')
  }
  const str = `
  /**
   * @name: 上传之前的文件判断
   * @description: 上传之前的文件判断，判断文件大小文件类型等
   * @param {*} file
   * @return {*}
   */  
  function ${conf.vModel}BeforeUpload(file) {
    ${rightSizeCode}
    ${acceptCode}
    return ${returnList.join('&&')}
  }`
  return returnList.length ? str : ''
}
/**
 * @name: 生成提交表单方法
 * @description: 生成提交表单方法
 * @param {Object} conf vModel 表单ref
 * @return {*}
 */
function buildSubmitUpload(conf) {
  const str = `function submitUpload() {
    this.$refs['${conf.vModel}'].submit()
  }`
  return str
}
