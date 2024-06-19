<script setup>
import { ref } from 'vue'
import { deliveryManageListService, deliveryManageDeleteService, deliveryManageAddService } from '@/api/delivery.js';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'

//定义数据搜索表单
const searchModel = ref({
  phone: '',
  deliveryName: '',
  deliveryPhone: '',
  addressKey: '',
  createTime: {
    min: '',
    max: ''
  }
})
//定义清空搜索表单的单击事件
const clearSearch = () => {
  searchModel.value.phone = ''
  searchModel.value.addressKey = ''
  searchModel.value.createTime.min = ''
  searchModel.value.createTime.max = ''
  searchModel.value.deliveryName = ''
  searchModel.value.deliveryPhone = ''
  searchModel.value.phone = ''
  searchModel.value.deliveryName = ''
  searchModel.value.deliveryPhone = ''
  searchModel.value.addressKey = ''
  searchModel.value.createTime.min = ''
  searchModel.value.createTime.max = ''
  //刷新页面
  deliverysList()
}

//定义显示的数据模型
const deliveryList = ref([])
//分页条数据模型
const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(3) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  deliverysList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  deliverysList()
}

//定义搜索和显示的单击事件的
const deliverysList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    phone: searchModel.value.phone ? searchModel.value.phone : null,
    deliveryName: searchModel.value.deliveryName ? searchModel.value.deliveryName : null,
    addressKey: searchModel.value.addressKey ? searchModel.value.addressKey : null,
    deliveryPhone: searchModel.value.deliveryPhone ? searchModel.value.deliveryPhone : null,
    createMin: searchModel.value.createTime.min ? searchModel.value.createTime.min : null,
    createMax: searchModel.value.createTime.max ? searchModel.value.createTime.max : null,
  }
  let result = await deliveryManageListService(params);

  //渲染视图
  total.value = result.data.total;
  deliveryList.value = result.data.items;
}
deliverysList();

//删除数据
const deliveryDelete = (row) => {
  ElMessageBox.confirm(
    '你确认要删除该条地址信息嘛？',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
      center: true,
    }
  )
    .then(async () => {
      //调用删除的接口
      console.log(row.deliveryIndex);
      let result = await deliveryManageDeleteService(row.deliveryIndex);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新页面
      deliverysList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '删除失败',
      })
    })
}

//定义弹窗数据
const dialogVisible = ref(false)
//定义新增表单数据
const deliveryAdd = ref({
  deliveryName: '',
  deliveryPhone: '',
  deliveryArea: '',
  deliveryFullAddress: '',
})
//定义新增事件
const deliveryManageAdd = async () => {
  //调用接口，提交数据
  let result = await deliveryManageAddService(deliveryAdd.value);
  //判断是否成功
  ElMessage.success(result.message ? result.message : "添加成功");
  //更改抽屉显示
  dialogVisible.value = false;
  //清空新建表单内容
  deliveryAdd.value.deliveryName = ''
  deliveryAdd.value.deliveryPhone = ''
  deliveryAdd.value.deliveryArea = ''
  deliveryAdd.value.deliveryFullAddress = ''
  //刷新页面
  deliverysList()
}

</script>



<template>
  <el-card class="page-container">
    <!-- 卡片头 -->
    <template #header>
      <div class="header">
        <span>地址管理</span>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible = true;">添加地址</el-button>
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form :model="searchModel">
      <el-row>
        <el-col :span="12">
          <el-form-item label="收货人电话">
            <el-input placeholder="请输入收货人电话" v-model="searchModel.deliveryPhone"
              style="position: relative;left: 20px; width: 200px;"></el-input>
          </el-form-item>

        </el-col>
        <el-col :span="12">
          <el-form-item label="收货人姓名">
            <el-input placeholder="请输入姓名" v-model="searchModel.deliveryName"
              style="position: relative;left: 20px; width: 300px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="创建日期">
            <el-date-picker v-model="searchModel.createTime.min" type="datetime" placeholder="选择创建最早日期"
              :default-time="defaultTime" style="position: relative;left: 33px; width: 170px;" />
            <el-date-picker v-model="searchModel.createTime.max" type="datetime" placeholder="选择创建最晚日期"
              :default-time="defaultTime" style="position: relative;left: 40px; width: 170px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地址关键词">
            <el-input placeholder="请输入关键词" v-model="searchModel.addressKey"
              style="position: relative;left: 20px; width: 300px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="地址所有者">
            <el-input placeholder="请输入手机号码" v-model="searchModel.phone"
              style="position: relative;left: 20px; width: 200px;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label-width="450">
            <el-button @click="clearSearch()">重置</el-button>
            <el-button type="primary" @click="deliverysList()">搜索</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider />

    <el-table :data="deliveryList" border style="width: 100%">
      <el-table-column label="id" prop="deliveryIndex" width="50"></el-table-column>
      <el-table-column label="收货人姓名" prop="deliveryName" width="120"></el-table-column>
      <el-table-column label="收货人电话" prop="deliveryPhone" width="120"> </el-table-column>
      <el-table-column label="所有者" prop="deliveryOwner" width="120"></el-table-column>
      <el-table-column label="收货区" prop="deliveryArea"></el-table-column>
      <el-table-column label="收货详细地址" prop="deliveryFullAddress"> </el-table-column>
      <el-table-column label="创建时间" prop="deliveryCreateTime"></el-table-column>

      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary"
            @click="dialogVisible = true; updateState.id = row.buyIndex; updateState.state = row.buyState"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deliveryDelete(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <el-dialog v-model="dialogVisible" title="创建新的地址" width="500" :before-close="handleClose">
      <el-form ref=" form" size="large" autocomplete="off" :model="deliveryAdd">
        <el-form-item label="收货人姓名">
          <el-input placeholder="请输入收货人姓名" v-model="deliveryAdd.deliveryName"
            style="position: relative;left: 30px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="收货人号码">
          <el-input placeholder="请输入收货人号码" v-model="deliveryAdd.deliveryArea"
            style="position: relative;left: 30px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="收货人所在区">
          <el-input placeholder="请输入用户密码" v-model="deliveryAdd.deliveryArea"
            style="position: relative;left: 17px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="收货人详细地址">
          <el-input placeholder="请输入用户密码" v-model="deliveryAdd.deliveryFullAddress"
            style="position: relative;left: 4px; width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false;" style="margin-right: 150px;margin-left: 80px;">取消</el-button>
            <el-button type="primary" @click="deliveryManageAdd()">
              添加
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>


    <!-- 搜索表单
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
    </el-dialog> -->
    <!-- 
    <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
      <el-form :model="goodModel" label-width="100px">
        <el-form-item label="商品价格">
          <el-input v-model="goodModel.goodPrice" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品数量">
          <el-input v-model="goodModel.goodNum" placeholder="请输入商品价格"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
            name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="imgUploadSuccess">
            <img v-if="goodModel.goodPic" :src="goodModel.goodPic" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品描述">
          <div class="editor">
            <quill-editor theme="snow" v-model:content="goodModel.goodDescribe" contentType="html">
            </quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="judgeGood()">发布</el-button>
        </el-form-item>
      </el-form>
    </el-drawer> -->
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