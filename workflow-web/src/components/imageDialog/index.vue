<template>
  <el-dialog
      title="查看流程图"
      width="600px"
      draggable
      append-to-body
      destroy-on-close
      v-model="dialogVisible">
    <div style="border: black solid 1px;border-radius: 6px;padding:10px 0;display: flex;justify-content: center;">
      <el-image :src="imagUrl" />
    </div>
    <template #footer>
      <div>
        <el-button @click="handleCancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script setup>
import {ref} from "vue";

const imagUrl =ref('')

// 弹窗打开标识
const dialogVisible = ref(false);
// 打开方法
const open = (processDefinitionId) => {
  console.log(processDefinitionId);
  imagUrl.value = `http://localhost:8888/flowable/process-definitions/processDefinition/${processDefinitionId}`;
  dialogVisible.value = true;
}
// 关闭方法
const close = () => dialogVisible.value = false;
// 取消方法
const handleCancel = () => {
  close();
}

defineExpose({
  open,
  close
})

</script>

<style scoped>

</style>