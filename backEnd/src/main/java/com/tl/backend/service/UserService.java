package com.tl.backend.service;

import com.tl.backend.pojo.User;

public interface UserService {
    User findByPhone(String phone);

    void register(String username, String phone, String password);

    void update(User user);

    void updatePic(String picUrl);

    void updatePwd(String new_pwd);
}
