//导入request
import request from "@/utils/request.js";

//请求分页查询
export const deliveryManageListService = (params) => {
  return request.get("/delivery", { params: params });
};

//删除地址记录
export const deliveryManageDeleteService = (id) => {
  return request.delete("/delivery?id=" + id);
};

//新增地址记录
export const deliveryManageAddService = (params) => {
  return request.post("/delivery", params);
};
