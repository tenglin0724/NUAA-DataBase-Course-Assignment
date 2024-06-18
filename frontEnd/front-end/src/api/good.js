//导入request
import request from "@/utils/request.js";

//请求分页查询
export const goodManageListService = (params) => {
  return request.get("/good", { params: params });
};

//请求删除记录
export const goodManageDeleteService = (id) => {
  return request.delete("/good?id=" + id);
};

//新增商品记录
export const goodManageAddService = (params) => {
  return request.post("/good", params);
};

//更新购买记录状态
export const goodManageUpdateService = (params) => {
  return request.put("/good", params);
};
