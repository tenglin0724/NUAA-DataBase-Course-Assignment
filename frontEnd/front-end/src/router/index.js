import { createRouter, createWebHistory } from "vue-router";

//导入组件
import LoginVue from "@/views/Login.vue";
import Layout from "@/views/Layout.vue";
import BuyManage from "@/views/buy/BuyManage.vue";
import BuyShow from "@/views/buy/BuyShow.vue";
import GoodManage from "@/views/good/GoodManage.vue";
import GoodShow from "@/views/good/GoodShow.vue";
import DeliveryManage from "@/views/delivery/DeliveryManage.vue";
import DeliveryShow from "@/views/delivery/DeliveryShow.vue";
import UserAvatar from "@/views/user/UserAvatar.vue";
import UserInfo from "@/views/user/UserInfo.vue";
import UserResetPassword from "@/views/user/UserResetPassword.vue";
import Manage from "@/views/Manage.vue";
import UserManage from "@/views/user/UserManage.vue";

//定义路由关系
const routes = [
  { path: "/login", component: LoginVue },
  {
    path: "/manage",
    component: Manage,
    redirect: "/good/manage",
    children: [
      { path: "/good/manage", component: GoodManage },
      { path: "/buy/manage", component: BuyManage },
      { path: "/user/manage", component: UserManage },
      { path: "/delivery/manage", component: DeliveryManage },
    ],
  },
  {
    path: "/",
    component: Layout,
    //配置重定向的路由
    redirect: "/login",
    //配置子路由
    children: [
      { path: "/good/show", component: GoodShow },
      { path: "/buy/show", component: BuyShow },
      { path: "/delivery/show", component: DeliveryShow },
      { path: "/user/avatar", component: UserAvatar },
      { path: "/user/info", component: UserInfo },
      { path: "/user/reSetPassword", component: UserResetPassword },
    ],
  },
];

//创建路由器
const router = createRouter({
  //选择history方式创建路由器
  history: createWebHistory(),
  //指定路由
  routes: routes,
});

//导出路由器
export default router;
