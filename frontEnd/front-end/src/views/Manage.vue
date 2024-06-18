<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  LocationFilled,
  GoodsFilled,
  WalletFilled
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b" background-color="#177cb0" text-color="black" router>
        <el-menu-item index="/good/manage">
          <el-icon>
            <GoodsFilled />
          </el-icon>
          <span>商品信息</span>
        </el-menu-item>
        <el-menu-item index="/buy/manage">
          <el-icon>
            <WalletFilled />
          </el-icon>
          <span>购买记录</span>
        </el-menu-item>
        <el-menu-item index="/user/manage">
          <el-icon>
            <WalletFilled />
          </el-icon>
          <span>用户信息</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>当前用户：<strong>管理员</strong></div>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <!-- <div style="width: 1240px; height: 530px;border: 1px solid red;"> -->
        <!-- 声明子路由 -->
        <router-view></router-view>
        <!-- </div> -->
      </el-main>
      <!-- 底部区域 -->
      <el-footer>©2024 Created by TL</el-footer>
    </el-container>
  </el-container>
</template>



<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #177cb0;

    &__logo {
      height: 120px;
      background: url('@/assets/02.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: white;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>