<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//管理员搜索的数据模型
const searchModel = ref({
    userPhone: '',
    keyWord: '',
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
    searchModel.value.userPhone = ''
    searchModel.value.keyWord = ''
    searchModel.value.dateInterval.min = ''
    searchModel.value.dateInterval.max = ''
    searchModel.value.priceInterval.min = ''
    searchModel.value.priceInterval.max = ''
    searchModel.value.numInterval.min = ''
    searchModel.value.numInterval.max = ''
    //刷新
    goodsList();
}

//展示的模型
const goods = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    goodsList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    goodsList()
}


//商品获取
import { goodManageListService, goodManageDeleteService, goodManageAddService, goodManageUpdateService } from '@/api/good.js';
const goodsList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        userPhone: searchModel.value.userPhone ? searchModel.value.userPhone : null,
        keyWord: searchModel.value.keyWord ? searchModel.value.keyWord : null,
        dateMin: searchModel.value.dateInterval.min ? searchModel.value.dateInterval.min : null,
        dateMax: searchModel.value.dateInterval.max ? searchModel.value.dateInterval.max : null,
        numMin: searchModel.value.numInterval.min ? searchModel.value.numInterval.min : null,
        numMax: searchModel.value.numInterval.max ? searchModel.value.numInterval.max : null,
        priceMin: searchModel.value.priceInterval.min ? searchModel.value.priceInterval.min : null,
        priceMax: searchModel.value.priceInterval.max ? searchModel.value.priceInterval.max : null,
    }
    let result = await goodManageListService(params);

    //渲染视图
    total.value = result.data.total;
    goods.value = result.data.items;
}

goodsList();


//删除记录
import { ElMessage, ElMessageBox } from 'element-plus';
const goodDelete = (row) => {
    ElMessageBox.confirm(
        '你确认要删除该条商品信息嘛？',
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
            let result = await goodManageDeleteService(row.goodIndex);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新页面
            goodsList();
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
const goodModel = ref({
    goodPic: '',
    goodDescribe: '',
    goodPrice: '',
    goodNum: '',
})

//图片上传函数
const imgUploadSuccess = (result) => {
    goodModel.value.goodPic = "D:\\devp\\pic\\" + result.data
    console.log(result.data);
}

//添加商品
const addGood = async () => {
    console.log(goodModel.value);
    //调用接口，提交数据
    let result = await goodManageAddService(goodModel.value);
    //判断是否成功
    ElMessage.success(result.message ? result.message : "添加成功")
    //更改抽屉显示
    visibleDrawer.value = false;
    //清空新建表单内容
    goodModel.value.goodPic = ''
    goodModel.value.goodDescribe = ''
    goodModel.value.goodPrice = ''
    goodModel.value.goodNum = ''
    //刷新页面
    goodsList()
}

//实现信息更新功能
const title = ref('')
const goodID = ref('')
const showDrawer = (row) => {
    title.value = '修改商品信息'
    visibleDrawer.value = true
    //绑定数据
    goodID.value = row.goodIndex
    goodModel.value.goodDescribe = row.goodDescribe
    goodModel.value.goodNum = row.goodNum
    goodModel.value.goodPic = row.goodPic
    goodModel.value.goodPrice = row.goodPrice
}

//修改商品信息
const updateGood = async () => {
    //封装参数
    const params = {
        goodIndex: goodID.value,
        goodDescribe: goodModel.value.goodDescribe,
        goodNum: goodModel.value.goodNum,
        goodPic: goodModel.value.goodPic,
        goodPrice: goodModel.value.goodPrice,
    }
    //发送请求
    let result = await goodManageUpdateService(params)
    //处理请求
    ElMessage.success(result.message ? result.message : "已修改")
    //更改抽屉显示
    visibleDrawer.value = false;
    //清空新建表单内容
    goodID.value = ''
    goodModel.value.goodPic = ''
    goodModel.value.goodDescribe = ''
    goodModel.value.goodPrice = ''
    goodModel.value.goodNum = ''
    //刷新页面
    goodsList()
}

//判断是add还是update
const judgeGood = () => {
    if (title.value === "添加商品") {
        addGood()
    } else if (title.value === "修改商品信息") {
        updateGood()
    } else {
        ElMessage.error("系统错误！")
    }
}

</script>
<template>
    <el-card class="page-container">
        <!-- 卡片头 -->
        <template #header>
            <div class="header">
                <span>商品管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true; title = '添加商品'">添加商品</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->

        <el-form :model="searchModel">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="关键词">
                        <el-input placeholder="请输入关键词" v-model="searchModel.keyWord"
                            style="position: relative;left: 20px; width: 300px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">

                    <el-form-item label="发布时间">
                        <el-date-picker v-model="searchModel.dateInterval.min" type="datetime" placeholder="选择起始时间"
                            :default-time="defaultTime" style="position: relative;left: 6px; width: 150px;" />
                        <el-date-picker v-model="searchModel.dateInterval.max" type="datetime" placeholder="选择结束时间"
                            :default-time="defaultTime" style="position: relative;left: 20px; width: 150px;" />
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
                            style="position: relative;left: 6px; width: 120px;"></el-input>
                        <el-input placeholder="商品最多数量" v-model="searchModel.numInterval.max"
                            style="position: relative;left: 60px; width: 120px;"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="所有者">
                        <el-input placeholder="请输入号码" v-model="searchModel.userPhone"
                            style="position: relative;left: 20px; width: 120px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label-width="450">
                        <el-button @click="clearSearch">重置</el-button>
                        <el-button type="primary" @click="goodsList()">搜索</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <el-divider />

        <!-- 商品列表 -->
        <el-table :data="goods" border style="width: 100%">
            <el-table-column label="id" prop="goodIndex" width="50"></el-table-column>
            <el-table-column label="商品所有者" prop="goodOwner" width="120"></el-table-column>
            <el-table-column label="商品简介" prop="goodDescribe"></el-table-column>
            <el-table-column label="商品价格" prop="goodPrice" width="90"> </el-table-column>
            <el-table-column label="商品数量" prop="goodNum" width="90"></el-table-column>
            <el-table-column label="商品图片" prop="goodPic"> </el-table-column>
            <el-table-column label="创建时间" prop="goodCreateTime"></el-table-column>
            <el-table-column label="更新时间" prop="goodUpdateTime"></el-table-column>
            <el-table-column label="操作" width="120">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDrawer(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="goodDelete(row)"></el-button>
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
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
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