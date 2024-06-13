package com.tl.backend.mapper;

import com.tl.backend.pojo.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Insert("insert into good(good_pic,good_describe,good_price,good_num,good_create_time,good_update_time,good_owner)" +
            "values (#{goodPic},#{goodDescribe},#{goodPrice},#{goodNum},#{goodCreateTime},#{goodUpdateTime},#{goodOwner})")
    void add(Good good);

    //使用配置文件写动态sql
    List<Good> list(@Param("phone") String phone,@Param("isMy") boolean isMy,@Param("min") Object min,@Param("max") Object max);

    @Update("update good set good_pic=#{goodPic},good_describe=#{goodDescribe},good_price=#{goodPrice},good_num=#{goodNum} where good_index=#{goodIndex}")
    void update(Good good);


    @Delete("delete from good where good_index=#{id}")
    void delete(@Param("id") Integer id);

    @Select("select * from good where good_index=#{id};")
    Good detail(@Param("id") Integer id);
}
