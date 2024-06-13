package com.tl.backend.mapper;


import com.tl.backend.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from my_user where phone=#{phone}")
    User findByPhone(@Param("phone") String phone);

    @Insert("insert into my_user(username,phone,password,user_create_time,user_update_time)" +
            "values (#{username},#{phone},#{password},now(),now())")
    void register(@Param("username") String username,@Param("phone") String phone,@Param("password") String password);

    @Update("update my_user set sex=#{sex},birthday=#{birthday},user_address=#{userAddress},user_update_time=#{userUpdateTime}")
    void update(User user);

    @Update("update my_user set user_pic=#{picUrl},user_update_time=now() where phone=#{phone}")
    void updatePic(@Param("picUrl") String picUrl, @Param("phone") String phone);

    @Update("update my_user set password=#{new_pwd},user_update_time=now() where phone=#{phone}")
    void updatePwd(@Param("new_pwd") String new_pwd,@Param("phone") String phone);
}
