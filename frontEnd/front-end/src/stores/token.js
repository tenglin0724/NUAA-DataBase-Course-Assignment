//定义store
import { defineStore } from "pinia";
import { ref } from "vue";

//第一个参数为名字
//第二个参数为函数
export const useTokenStore = defineStore(
  "token",
  () => {
    //定义状态的内容
    //1、响应式变量
    const token = ref("");
    //2、修改token的值
    const setToken = (newToken) => {
      token.value = newToken;
    };
    //3、函数，移除token的值
    const removeToken = () => {
      token.value = "";
    };
    //返回定义的内容
    return {
      token,
      setToken,
      removeToken,
    };
  },
  {
    //持久化存储
    persist: true,
  }
);
