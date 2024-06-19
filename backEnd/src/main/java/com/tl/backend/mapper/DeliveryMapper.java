package com.tl.backend.mapper;

import com.tl.backend.pojo.Delivery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    @Insert("insert into delivery(delivery_name,delivery_area,delivery_full_address,delivery_phone,delivery_create_time,delivery_owner)" +
            "values (#{deliveryName},#{deliveryArea},#{deliveryFullAddress},#{deliveryPhone},#{deliveryCreateTime},#{deliveryOwner})")
    void add(Delivery delivery);

//    @Select("select * from delivery where delivery_owner=#{phone}")
//    List<Delivery> list(@Param("phone") String phone);

    @Select("select * from delivery where delivery_index=#{id};")
    Delivery detail(@Param("id") Integer id);

    @Update("update delivery set delivery_name=#{deliveryName},delivery_area=#{deliveryArea},delivery_full_address=#{deliveryFullAddress},delivery_phone=#{deliveryPhone},delivery_create_time=#{deliveryCreateTime} where delivery_index=#{deliveryIndex}")
    void update(Delivery delivery);

    @Delete("delete from delivery where delivery_index=#{id}")
    void delete(Integer id);

    //使用配置文件
    List<Delivery> list(@Param("myPhone") String myPhone,@Param("isMy") boolean isMy,@Param("phone") String phone,@Param("deliveryName") String deliveryName,@Param("deliveryPhone") String deliveryPhone, @Param("addressKey") String addressKey,@Param("createMin") Object createMin,@Param("createMax") Object createMax);
}
