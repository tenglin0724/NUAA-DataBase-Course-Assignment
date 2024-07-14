package com.tl.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tl.backend.mapper.UserMapper;
import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.User;
import com.tl.backend.service.UserService;
import com.tl.backend.util.CamelToUnderlineUtil;
import com.tl.backend.util.Md5Util;
import com.tl.backend.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean<User> list(Integer pageNum, Integer pageSize, String phone, String userName, String sex, String address, Object brithMin, Object brithMax, Object createMin, Object createMax,String prop,String order) {
        //判断prop是否为空
        if(prop!=null){
            prop= CamelToUnderlineUtil.camel2under(prop);
        }

        //1. 创建PageBean对象
        PageBean<User> pb = new PageBean<>();
        //2. 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //3. 调用Mapper

        List<User> users = userMapper.list(phone, userName, sex, address, brithMin, brithMax,createMin,createMax,prop,order);
        //page中提供了方法，可以获取PageHelper分页查询获得的总记录条数和对应的数据
        Page<User> p =(Page<User>)users;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

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

    @Override
    public void delete(String phone) {
        userMapper.delete(phone);
    }


}
