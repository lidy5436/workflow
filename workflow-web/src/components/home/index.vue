<template>
  <div style="height: 100%">
    <el-container style="height: 100%">
      <el-aside width="200px" style="background: #545c64;height: 100%">
        <el-menu
            mode="vertical"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            :default-active="activeIndex">
          <el-menu-item index="1" route="/processDefinition">
            流程定义
          </el-menu-item>

        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="display: flex;align-items: center;justify-content: space-between">
          <div>流程引擎管理系统</div>
          <div>当前登录用户: 李东阳</div>
        </el-header>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>

import {ref} from "vue";
import {Edit} from "@element-plus/icons-vue";

const activeIndex = ref("0")

</script>

<style scoped>

</style>