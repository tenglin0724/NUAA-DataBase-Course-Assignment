package com.tl.backend;

import com.tl.backend.mapper.GoodMapper;
import com.tl.backend.mapper.UserMapper;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class BackEndApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Test
    void mapper() {
        User user=new User();
        user.setPhone("19103878295");
        user.setUserAddress("ÄÏ¾©ÊÐ");
        user.setSex("ÄÐ");
        user.setUserUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }
    @Test
    void goodMapper(){
           Good good=new Good();
           good.setGoodPic("https://www.bilibili.com/");
           good.setGoodCreateTime(Timestamp.valueOf(LocalDateTime.now()));
           good.setGoodUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
           good.setGoodDescribe("hao");
           good.setGoodOwner("19103878295");
           good.setGoodPrice(112.12);
           good.setGoodNum(12);
           goodMapper.add(good);
    }
}
