<template>
  <el-dialog
      title="新增流程定义"
      draggable
      append-to-body
      destroy-on-close
      v-model="dialogVisible">
    <el-form v-model="form">
      <el-form-item label="流程名称" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item label="资源文件" prop="resourceName">
        <el-input v-model="form.resourceName"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div>
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import {ref} from "vue";
import {processDeploymentApi} from "@/apis/processDefinition.js";
import {ElMessage} from "element-plus";
// 表单
const form = ref({
  name: undefined,
  resourceName: undefined
})
// 弹窗打开标识
const dialogVisible = ref(false);
// 打开方法
const open = () => dialogVisible.value = true;
// 关闭方法
const close = () => dialogVisible.value = false;
// 取消方法
const handleCancel = () => {
  close();
}
// 确认方法
const handleSubmit = async () => {
  try {
    const query = form.value
    const response = await processDeploymentApi(query)
    const res = response.data;
    const {code, msg} = res;
    if (code === 200) {
      ElMessage.success(msg)
    }else {
      ElMessage.error(msg)
    }
  } catch (e) {
    console.error(e)
  }
  close()
}


defineExpose({
  open,
  close
})

</script>

<style scoped>

</style>