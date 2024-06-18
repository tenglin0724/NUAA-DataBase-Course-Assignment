<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'
//管理员搜索的数据模型
const searchModel = ref({
  buyUserPhone: '',
  buyGoodIndex: '',
  buyDeliveryIndex: '',
  buyState: '',
  dateInterval: {
    min: '',
    max: ''
  },
  numInterval: {
    min: '',
    max: ''
  },
  priceInterval: {
    min: '',
    max: ''
  }
})
//清空事件
const clearSearch = () => {
  searchModel.value.buyUserPhone = ''
  searchModel.value.buyGoodIndex = ''
  searchModel.value.buyDeliveryIndex = ''
  searchModel.value.buyState = ''
  searchModel.value.dateInterval.min = ''
  searchModel.value.dateInterval.max = ''
  searchModel.value.priceInterval.min = ''
  searchModel.value.priceInterval.max = ''
  searchModel.value.numInterval.min = ''
  searchModel.value.numInterval.max = ''
  //刷新
  buysList();
}

//校验表单规则
const rules = {
}

//展示的数据模型
const buys = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  buysList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  buysList()
}

//购买记录获取
import { buyManageListService, buyManageDeleteService, buyManageAddService } from '@/api/buy.js';
const buysList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    userPhone: searchModel.value.userPhone ? searchModel.value.userPhone : null,
    goodIndex: searchModel.value.buyGoodIndex ? searchModel.value.buyGoodIndex : null,
    deliveryIndex: searchModel.value.buyDeliveryIndex ? searchModel.value.buyDeliveryIndex : null,
    state: searchModel.value.buyState ? searchModel.value.buyState : null,
    dateMin: searchModel.value.dateInterval.min ? searchModel.value.dateInterval.min : null,
    dateMax: searchModel.value.dateInterval.max ? searchModel.value.dateInterval.max : null,
    numMin: searchModel.value.numInterval.min ? searchModel.value.numInterval.min : null,
    numMax: searchModel.value.numInterval.max ? searchModel.value.numInterval.max : null,
    priceMin: searchModel.value.priceInterval.min ? searchModel.value.priceInterval.min : null,
    priceMax: searchModel.value.priceInterval.max ? searchModel.value.priceInterval.max : null,
  }
  let result = await buyManageListService(params);

  //渲染视图
  total.value = result.data.total;
  buys.value = result.data.items;
}
//让数据保留两位小数
const fixTable = (value) => {
  return Number(value).toFixed(2);
}

buysList();


//删除记录
import { ElMessage, ElMessageBox } from 'element-plus';
const buyDelete = (row) => {
  ElMessageBox.confirm(
    '你确认要删除该条购买记录嘛？',
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
      let result = await buyManageDeleteService(row.buyIndex);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //刷新页面
      buysList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '删除失败',
      })
    })
}

//新增数据
import { Plus } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore()
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const buyModel = ref({
  buyGoodIndex: '',
  buyNum: '',
  buyDeliveryIndex: '',
})

//添加购买记录
const addBuy = async () => {
  //调用接口，提交数据
  let result = await buyManageAddService(buyModel.value);
  //判断是否成功
  ElMessage.success(result.message ? result.message : "添加成功")
  //更改抽屉显示
  visibleDrawer.value = false;
  //清空新建表单内容
  buyModel.value.buyGoodIndex = ''
  buyModel.value.buyNum = ''
  buyModel.value.buyDeliveryIndex = ''
  //刷新页面
  buysList()
}

// //定义动态数据，定义抽屉的标题
// const buyTitle = ref('')

// //定义修改记录事件
// const showDrawer = (row) => {
//   visibleDrawer.value = true
//   buyTitle.value = "修改购买记录"
//   //复制数据
//   buyModel.value.buyGoodIndex =
//     buyModel.value.buyNum = ''
//   buyModel.value.buyDeliveryIndex = ''
// }



</script>
<template>
  <el-card class="page-container">
    <!-- 卡片头 -->
    <template #header>
      <div class="header">
        <span>购买记录管理</span>
        <div class="extra">
          <el-button type="primary" @click="visibleDrawer = true; buyTitle = '添加购买记录'">添加购买记录</el-button>
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form :model="searchModel" :rules="rules">
      <el-row>
        <el-col :span="12">
          <el-form-item label="购买者">
            <el-input placeholder="请输入购买者电话" v-model="searchModel.buyUserPhone"
              style="position: relative;left: 20px; width: 200px;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购买商品">
            <el-input placeholder="请输入购买商品" v-model="searchModel.buyGoodIndex"
              style="position: relative;left: 20px; width: 200px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="商品价格" v-model="searchModel.priceInterval">
            <el-input placeholder="商品最低价格" v-model="searchModel.priceInterval.min"
              style="position: relative;left: 6px; width: 120px;"></el-input>
            <el-input placeholder="商品最高价格" v-model="searchModel.priceInterval.max"
              style="position: relative;left: 60px; width: 120px;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品数量">
            <el-input placeholder="商品最少数量" v-model="searchModel.numInterval.min"
              style="position: relative;left: 20px; width: 120px;"></el-input>
            <el-input placeholder="商品最多数量" v-model="searchModel.numInterval.max"
              style="position: relative;left: 60px; width: 120px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="购买时间">
            <el-date-picker v-model="searchModel.dateInterval.min" type="datetime" placeholder="选择起始时间"
              :default-time="defaultTime" style="position: relative;left: 6px; width: 150px;" />
            <el-date-picker v-model="searchModel.dateInterval.max" type="datetime" placeholder="选择结束时间"
              :default-time="defaultTime" style="position: relative;left: 20px; width: 150px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="派送地址">
            <el-input placeholder="请输入派送地址" v-model="searchModel.buyDeliveryIndex"
              style="position: relative;left: 20px; width: 200px;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12"></el-col>
        <el-col :span="12">
          <el-form-item label-width="450">
            <el-button @click="clearSearch()">重置</el-button>
            <el-button type="primary" @click="buysList()">搜索</el-button>
          </el-form-item>
        </el-col>

      </el-row>
    </el-form>

    <el-divider />

    <!-- 商品列表 -->
    <el-table :data="buys" border style="width: 100%">
      <el-table-column label="id" prop="buyIndex" width="50"></el-table-column>
      <el-table-column label="购买者" prop="buyUserPhone" width="120"></el-table-column>
      <el-table-column label="购买商品" prop="buyGoodIndex" width="90"></el-table-column>
      <el-table-column label="购买地址" prop="buyDeliveryIndex" width="90"> </el-table-column>
      <el-table-column label="购买时间" prop="buyCreateTime"></el-table-column>
      <el-table-column label="更新时间" prop="buyUpdateTime"> </el-table-column>
      <el-table-column label="购买数量" prop="buyNum" width="90"></el-table-column>
      <el-table-column label="购买价格" prop="buyPrice" width="90"></el-table-column>
      <el-table-column label="购买状态" prop="buyState" width="90"></el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="showDrawer(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="buyDelete(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" :title="buyTitle" direction="rtl" size="50%">
      <!-- 添加文章表单 -->
      <el-form :model="buyModel" label-width="100px">
        <el-form-item label="商品ID">
          <el-input v-model="buyModel.buyGoodIndex" placeholder="请输入商品ID"></el-input>
        </el-form-item>
        <el-form-item label="购买数量">
          <el-input v-model="buyModel.buyNum" placeholder="请输入商品数量"></el-input>
        </el-form-item>
        <el-form-item label="派送地址">
          <el-input v-model="buyModel.buyDeliveryIndex" placeholder="请输入派送地址"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addBuy()">发布</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
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

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>