<script setup>
import { User, Lock, Iphone } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';
//引入token状态管理
import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore()


//创建路由
const router = useRouter()

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)


//注册账号的数据结构
const registerData = ref({
  username: '',
  phone: '',
  password: '',
  rePassword: ''
})

//校验rePassword
const checkRePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error("请再次输入验证密码"))
  } else if (value !== registerData.value.password) {
    callback(new Error("请确保两次输入的密码保持一致"))
  } else {
    callback()
  }
}


//校验表单规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', triggle: 'blur' },
    { min: 1, message: '用户名不能为空', triggle: 'blur' },
    { max: 20, message: '用户名不能过长', triggle: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入您的手机号码', triggle: 'blur' },
    { min: 7, max: 15, message: '请输入正确的电话号码', triggle: 'blur' }
  ],
  password: [
    // TODO:待完善
    { required: true, message: '请输入您的账号密码', triggle: 'blur' },
    { min: 6, max: 20, message: '输入的账号密码长度应为6~20', triggle: 'blur' }
  ],
  rePassword: [
    { validator: checkRePassword, triggle: 'blur' }
  ],
}


//调用后台接口完成注册
import { userRegisterService, userLoginService } from '@/api/user';
const register = async () => {
  let result = await userRegisterService(registerData.value)
  ElMessage({ type: 'success', message: result.message ? result.message : "注册成功" })
  router.push("/login")                                //刷新页面
}

//调用后台接口完成登录
const login = async () => {
  let result = await userLoginService(registerData.value)
  tokenStore.setToken(result.data)
  ElMessage({ type: 'success', message: result.message ? result.message : "登录成功" })
  router.push("/")
}


//实现清空函数
const clearRegisterData = () => {
  registerData.value = {
    username: '',
    phone: '',
    password: '',
    rePassword: ''
  }
}


//跳转到管理员界面
const dialogVisible = ref(false)

//跳转到管理员界面
const loginManage = async () => {
  if (registerData.value.phone !== "19103878295") {
    ElMessage.error("该账号非管理员用户，不可登录")
  } else {
    let result = await userLoginService(registerData.value)
    tokenStore.setToken(result.data)
    ElMessage({ type: 'success', message: result.message ? result.message : "登录成功" })
    router.push("/manage")
  }
  clearRegisterData();
}


</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input :prefix-icon="Iphone" placeholder="请输入手机号码" v-model="registerData.phone"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
            v-model="registerData.rePassword"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false; clearRegisterData()">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>



      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input :prefix-icon="Iphone" placeholder="请输入您的手机号码" v-model="registerData.phone"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
            v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true; clearRegisterData()">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>


      <!-- 切换管理员界面 -->
      <el-link type="primary" :underline="true" @click="dialogVisible = true" style="margin:0 auto;">
        我是管理员
      </el-link>

      <!-- 对话弹窗 -->
      <el-dialog v-model="dialogVisible" title="管理员登录" width="500" :before-close="handleClose">

        <el-form ref=" form" size="large" autocomplete="off" :model="registerData" :rules="rules">
          <el-form-item prop="phone">
            <el-input :prefix-icon="Iphone" placeholder="请输入您的手机号码" v-model="registerData.phone"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
              v-model="registerData.password"></el-input>
          </el-form-item>
          <el-form-item>
            <div class="dialog-footer">
              <el-button @click="dialogVisible = false; clearRegisterData()">取消</el-button>
              <el-button type="primary" @click="dialogVisible = false; loginManage();">
                登录
              </el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-dialog>

    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    // background: url('@/assets/02.png') no-repeat 60% center / 240px auto,
    background: url('@/assets/01.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>