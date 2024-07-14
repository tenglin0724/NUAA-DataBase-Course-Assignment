//导入request
import request from "@/utils/request.js";

//请求分页查询
export const buyManageListService = (params) => {
  return request.get("/buy", { params: params });
};

//请求删除记录
export const buyManageDeleteService = (id) => {
  return request.delete("/buy?id=" + id);
};

//新增购买记录
export const buyManageAddService = (params) => {
  return request.post("/buy", params);
};

//更新购买记录状态
export const buyManageUpdateService = (id, state) => {
  return request.put("/buy?id=" + id + "&state=" + state);
};

//获取连接查询用户信息
export const buyManageDetailUserService = (id) => {
  return request.get("/buy/user?id=" + id);
};

//获取连接查询商品信息
export const buyManageDetailGoodService = (id) => {
  return request.get("/buy/good?id=" + id);
};

//获取连接查询地址信息
export const buyManageDetailDeliveryService = (id) => {
  return request.get("/buy/delivery?id=" + id);
};
