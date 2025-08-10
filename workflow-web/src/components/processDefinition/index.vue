<template>
  <div style="padding: 20px">
    <el-card>
      <div style="padding: 10px 0">
        <el-button type="primary" :icon="Plus">新增流程</el-button>
      </div>
      <el-table :data="listData" border>
        <el-table-column prop="id" label="流程定义ID" align="center" width="340"/>
        <el-table-column prop="name" label="流程名称" align="center"/>
        <el-table-column prop="key" label="流程标识" align="center"/>
        <el-table-column prop="version" label="版本号" align="center" width="80"/>
        <el-table-column prop="deploymentId" label="部署ID" align="center" width="340"/>
        <el-table-column prop="resourceName" label="资源文件名" align="center" width="340"/>
        <el-table-column prop="deploymentTime" label="部署时间" align="center" width="180"/>
        <el-table-column prop="suspended" label="是否挂起" align="center"/>
        <el-table-column label="操作" align="center">
          <el-button type="text">激活</el-button>
        </el-table-column>
      </el-table>
      <div style="padding: 20px 0;display: flex;flex-direction: row-reverse">
        <el-pagination background layout="total,sizes,prev,pager,next,jumper" :total="page.total" />
      </div>
    </el-card>

  </div>
</template>

<script setup>

import {pageApi} from "@/apis/processDefinition.js";
import {onMounted, ref} from "vue";
import {Plus} from "@element-plus/icons-vue";

const listData = ref([])
const page = ref({
  total: 0,
  pageSize: 10,
  pageNum: 1,
})

const getList = async () => {
  try {
    const query = {};
    const response = await pageApi(query);
    const res = response.data;
    const {code, data} = res;
    if (code === 200) {
      const {list, total, pageSize, pageNum} = data;
      listData.value = list;
      page.value.total = total;
      page.value.pageNum = pageNum;
      page.value.pageSize = pageSize;
    } else {
      console.log(res);
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>