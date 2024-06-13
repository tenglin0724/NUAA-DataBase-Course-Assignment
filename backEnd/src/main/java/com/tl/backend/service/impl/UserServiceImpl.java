package com.tl.backend.service.impl;

import com.tl.backend.mapper.UserMapper;
import com.tl.backend.pojo.User;
import com.tl.backend.service.UserService;
import com.tl.backend.util.Md5Util;
import com.tl.backend.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByPhone(String phone) {
        User u = userMapper.findByPhone(phone);
        return u;
    }

    @Override
    public void register(String username, String phone, String password) {
        //进行加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.register(username,phone,md5String);
    }

    @Override
    public void update(User user) {
        user.setUserUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }

    @Override
    public void updatePic(String picUrl) {
        //从拦截器的ThreadLocalUtil中获取phone
        Map<String,Object> map=ThreadLocalUtil.get();
        String phone =(String)map.get("phone");
        userMapper.updatePic(picUrl,phone);
    }

    @Override
    public void updatePwd(String new_pwd) {
        //从拦截器的ThreadLocalUtil中获取phone
        Map<String,Object> map=ThreadLocalUtil.get();
        String phone =(String)map.get("phone");
        //这里应将new_pwd转换成MD5码
        userMapper.updatePwd(Md5Util.getMD5String(new_pwd),phone);
    }


}
