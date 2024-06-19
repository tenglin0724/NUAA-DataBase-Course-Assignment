package com.tl.backend.service;

import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.User;

public interface UserService {

    //创建列表查询
    PageBean<User> list(Integer pageNum, Integer pageSize, String phone, String userName,String sex, String address, Object brithMin, Object brithMax, Object createMin, Object createMax);

    User findByPhone(String phone);

    void register(String username, String phone, String password);

    void update(User user);

    void updatePic(String picUrl);

    void updatePwd(String new_pwd);

    //删除用户
    void delete(String phone);
}
