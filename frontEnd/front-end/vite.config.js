import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  //配置代理
  server: {
    proxy: {
      "/api": {
        //获取路径中包含了/api的请求
        target: "http://localhost:8080",
        changeOrigin: true, //修改源
        rewrite: (path) => path.replace(/^\/api/, ""), //将请求路径中所有的api替换成空字符串
      },
    },
  },
});
