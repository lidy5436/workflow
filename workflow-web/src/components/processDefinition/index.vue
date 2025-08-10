<template>
  <div style="padding: 20px">
    <el-card>
      <div style="padding: 20px 0 0 0">
        <el-form :model="searchForm">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item prop="nameLike" label="流程名称">
                <el-input v-model="searchForm.nameLike" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="key" label="流程标识">
                <el-input v-model="searchForm.key" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="deploymentId" label="流程部署ID">
                <el-input v-model="searchForm.deploymentId" clearable/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
              <el-button :icon="Refresh" @click="handleRefresh">重置</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div style="padding: 10px 0">
        <el-button type="primary" :icon="Plus">新增流程</el-button>
      </div>
      <el-table :data="listData" border height="550">
        <el-table-column prop="id" label="流程定义ID" align="center" width="340"/>
        <el-table-column prop="name" label="流程名称" align="center"/>
        <el-table-column prop="key" label="流程标识" align="center"/>
        <el-table-column prop="version" label="版本号" align="center" width="80"/>
        <el-table-column prop="deploymentId" label="部署ID" align="center" width="340"/>
        <el-table-column prop="resourceName" label="资源文件名" align="center" width="340"/>
        <el-table-column prop="deploymentTime" label="部署时间" align="center" width="180"/>
        <el-table-column prop="suspended" label="状态" align="center">
          <template #default="{row}">
            <span v-if="row.suspended===true">挂起</span>
            <span v-if="row.suspended===false">激活</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="{row}">
            <el-button v-if="row.suspended===false" type="text" @click="handleToggleSuspend(row)">挂起</el-button>
            <el-button v-if="row.suspended===true" type="text" @click="handleToggleSuspend(row)">激活</el-button>
            <el-button type="text">查看流程图</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 20px 0;display: flex;flex-direction: row-reverse">
        <el-pagination
            v-model:current-page="page.pageNum"
            v-model:page-size="page.pageSize"
            background
            layout="total,sizes,prev,pager,next,jumper"
            :total="page.total"
            @current-change="currentChange"
            @size-change="sizeChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>

import {pageApi, toggleSuspendApi} from "@/apis/processDefinition.js";
import {onMounted, ref} from "vue";
import {Plus, Refresh, Search} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

// 搜索表单
const searchForm = ref({
  nameLike: undefined,
  key: undefined,
  deploymentId: undefined,
});
// 列表数据
const listData = ref([])
// 分页数据
const page = ref({
  total: 0,
  pageSize: 10,
  pageNum: 1,
})
// 搜索方法
const handleSearch = () => {
  getList()
}
// 重置方法
const handleRefresh = () => {
  searchForm.value = {
    nameLike: undefined,
    key: undefined,
    deploymentId: undefined,
  }
  getList()
}
// 获取列表数据
const getList = async () => {
  try {
    const query = Object.assign({
      pageSize: page.value.pageSize,
      pageNum: page.value.pageNum,
    }, searchForm.value);
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
// 切换当前页
const currentChange = (value) => {
  page.value.pageNum = value;
  getList();
}
// 每页数量
const sizeChange = (value) => {
  page.value.pageSize = value;
  getList();
}
// 激活与挂起操作
const toggleSuspend = async (row) => {
  try {
    const query = {
      processDefinitionId: row.id,
    }
    const response = await toggleSuspendApi(query);
    const res = response.data;
    const { code, data,msg } = res;
    if (code === 200) {
      ElMessage.success(data)
    }else {
      ElMessage.error(msg)
    }
    await getList()
  }catch (e) {
    console.error(e)
  }
}

// 激活与挂起
const handleToggleSuspend = (row) => {
  const status = row.suspended?'激活':'挂起';
  ElMessageBox.confirm(`是否确认${status}当前流程?`, `${status}`, {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(()=> {
    toggleSuspend(row)

  })

}

onMounted(() => {
  getList();
})
</script>

<style scoped>

</style>