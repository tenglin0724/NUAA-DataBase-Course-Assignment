<script setup>
import { ref } from 'vue'
import { userManageListService, userManageDeleteService, userRegisterService } from '@/api/user';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'


//定义搜索表单数据模型
const searchModel = ref({
  phone: '',
  userName: '',
  sex: '',
  address: '',
  brithdate: {
    min: '',
    max: ''
  },
  createTime: {
    min: '',
    max: ''
  }
})

//定义清空搜索表单的单击事件
const clearSearch = () => {
  searchModel.value.phone = ''
  searchModel.value.userName = ''
  searchModel.value.sex = ''
  searchModel.value.address = ''
  searchModel.value.brithdate.min = ''
  searchModel.value.brithdate.max = ''
  searchModel.value.createTime.min = ''
  searchModel.value.createTime.max = ''
  //刷新页面
  usersList()
}

//定义显示的数据模型
const userList = ref([])
//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  usersList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  usersList()
}

//定义搜索和显示的单击事件的
const usersList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    phone: searchModel.value.phone ? searchModel.value.phone : null,
    userName: searchModel.value.userName ? searchModel.value.userName : null,
    sex: searchModel.value.sex ? searchModel.value.sex : null,
    address: searchModel.value.address ? searchModel.value.address : null,
    brithMin: searchModel.value.brithdate.min ? searchModel.value.brithdate.min : null,
    brithMax: searchModel.value.brithdate.max ? searchModel.value.brithdate.max : null,
    createMin: searchModel.value.createTime.min ? searchModel.value.createTime.min : null,
    createMax: searchModel.value.createTime.max ? searchModel.value.createTime.max : null,
  }
  let result = await userManageListService(params);
  //渲染视图
  total.value = result.data.total;
  userList.value = result.data.items;
}
usersList();

//删除用户
const userDelete = (row) => {
  ElMessageBox.confirm(
    '你确认要删除该用户嘛？',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
      center: true,
    }
  )
    .then(async () => {
      //判断是否是管理员用户
      if (row.phone === '19103878295') {
        ElMessage({
          type: 'error',
          message: '管理员用户不可删除'
        })
      } else {
        //调用删除的接口
        console.log(row.phone);
        let result = await userManageDeleteService(row.phone);
        console.log(result);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        //刷新页面
        usersList();
      }
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '删除失败',
      })
    })
}

//定义弹窗
const dialogVisible = ref(false)
//定义新增用户的数据模型
const userAdd = ref({
  username: '',
  phone: '',
  password: ''
})

//定义单击事件添加用户
const userManageAdd = async () => {
  if (userAdd.value.username === '' || userAdd.value.phone === '' || userAdd.value.password === '') {
    ElMessage.error("请将信息补充完整后添加")
  } else {
    console.log(userAdd.value);
    let result = await userRegisterService(userAdd.value);
    ElMessage.success(result.message ? result.message : "添加成功")
    usersList()
    //隐藏弹窗
    dialogVisible.value = false
    //清空表单
    userAdd.value.username = ''
    userAdd.value.phone = ''
    userAdd.value.password = ''

  }
}

</script>

<template>
  <el-card class="page-container">
    <!-- 卡片头 -->
    <template #header>
      <div class="header">
        <span>用户管理</span>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible = true;">添加用户</el-button>
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form :model="searchModel">
      <el-row>
        <el-col :span="12">
          <el-form-item label="手机号码">
            <el-input placeholder="请输入手机号码" v-model="searchModel.phone"
              style="position: relative;left: 20px; width: 300px;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户姓名">
            <el-input placeholder="请输入姓名" v-model="searchModel.userName"
              style="position: relative;left: 20px; width: 300px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="出生日期">
            <el-date-picker v-model="searchModel.brithdate.min" type="datetime" placeholder="选择出生最早日期"
              :default-time="defaultTime" style="position: relative;left: 20px; width: 170px;" />
            <el-date-picker v-model="searchModel.brithdate.max" type="datetime" placeholder="选择出生最晚日期"
              :default-time="defaultTime" style="position: relative;left: 30px; width: 170px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="常驻地址">
            <el-input placeholder="请输入地址" v-model="searchModel.address"
              style="position: relative;left: 20px; width: 300px;"></el-input>
          </el-form-item>


        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="创建时间">
            <el-date-picker v-model="searchModel.createTime.min" type="datetime" placeholder="选择创建最早日期"
              :default-time="defaultTime" style="position: relative;left: 20px; width: 170px;" />
            <el-date-picker v-model="searchModel.createTime.max" type="datetime" placeholder="选择创建最晚日期"
              :default-time="defaultTime" style="position: relative;left: 30px; width: 170px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="选择性别">
            <el-radio-group v-model="searchModel.sex">
              <el-radio value="男" size="large" border style="position: relative;left: 20px; width: 100px;">男性</el-radio>
              <el-radio value="女" size="large" border style="position: relative;left: 20px; width: 100px;">女性</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12"></el-col>
        <el-col :span="12">
          <el-form-item label-width="450">
            <el-button @click="clearSearch()">重置</el-button>
            <el-button type="primary" @click="usersList()">搜索</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider />
    <!-- 表单数据 -->
    <el-table :data="userList" border style="width: 100%">
      <el-table-column label="用户号码" prop="phone" width="150"></el-table-column>
      <el-table-column label="用户姓名" prop="username" width="100"></el-table-column>
      <el-table-column label="用户性别" prop="sex" width="90"> </el-table-column>
      <el-table-column label="用户生日" prop="brithday"></el-table-column>
      <el-table-column label="用户地址" prop="userAddress"> </el-table-column>
      <el-table-column label="创建时间" prop="userCreateTime"></el-table-column>
      <el-table-column label="更新时间" prop="userUpdateTime"></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button :icon="Delete" circle plain type="danger" @click="userDelete(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <!-- 弹窗 -->
    <el-dialog v-model="dialogVisible" title="创建新用户" width="500" :before-close="handleClose">
      <el-form ref=" form" size="large" autocomplete="off" :model="userAdd">
        <el-form-item label="用户名">
          <el-input placeholder="请输入用户名" v-model="userAdd.username"
            style="position: relative;left: 20px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="用户号码">
          <el-input placeholder="请输入用户号码" v-model="userAdd.phone"
            style="position: relative;left: 6px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input placeholder="请输入用户密码" v-model="userAdd.password"
            style="position: relative;left: 6px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false;" style="margin-right: 150px;margin-left: 80px;">取消</el-button>
            <el-button type="primary" @click="userManageAdd()">
              添加
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-card>
</template>
<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .el-form__body-wrapper {
    overflow: hidden;
  }

  .el-form__body-wrapper td {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>
