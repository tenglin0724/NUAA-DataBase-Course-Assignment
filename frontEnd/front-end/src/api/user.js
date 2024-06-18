//导入request
import request from "@/utils/request.js";

//用户注册信息提交
export const userRegisterService = (registerData) => {
  //由于这里的参数是xxx-form形式，直接传递会是json格式，不匹配，所以采用urlSearchParams进行传参
  const params = new URLSearchParams();
  for (let key in registerData) {
    params.append(key, registerData[key]);
  }
  //返回相应数据
  return request.post("/user/register", params);
};

//用户登录信息提交
export const userLoginService = (loginData) => {
  const params = new URLSearchParams();
  for (let key in loginData) {
    params.append(key, loginData[key]);
  }
  //返回相应数据
  return request.post("/user/login", params);
};
