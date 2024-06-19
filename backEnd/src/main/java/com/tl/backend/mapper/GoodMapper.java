package com.tl.backend.mapper;

import com.tl.backend.pojo.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Insert("insert into good(good_pic,good_describe,good_price,good_num,good_create_time,good_update_time,good_owner)" +
            "values (#{goodPic},#{goodDescribe},#{goodPrice},#{goodNum},#{goodCreateTime},#{goodUpdateTime},#{goodOwner})")
    void add(Good good);

    @Update("update good set good_pic=#{goodPic},good_describe=#{goodDescribe},good_price=#{goodPrice},good_num=#{goodNum},good_update_time=#{goodUpdateTime} where good_index=#{goodIndex}")
    void update(Good good);


    @Delete("delete from good where good_index=#{id}")
    void delete(@Param("id") Integer id);

    @Select("select * from good where good_index=#{id};")
    Good detail(@Param("id") Integer id);

    //使用配置文件写动态sql
    List<Good> list(@Param("myPhone") String myPhone,@Param("isMy") boolean isMy,@Param("userPhone") String userPhone,@Param("keyWord") String keyWord,@Param("priceMin") Object priceMin,@Param("priceMax") Object priceMax,@Param("dateMin") Object dateMin,@Param("dateMax") Object dateMax,@Param("numMin") Object numMin,@Param("numMax") Object numMax,@Param("prop") String prop,@Param("order") String order);
}
