package com.tl.backend.mapper;

import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Delivery;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuyMapper {
    @Insert("insert into buy(buy_good_index,buy_user_phone,buy_create_time,buy_update_time,buy_num,buy_price,buy_state,buy_delivery_index)" +
            "values (#{buyGoodIndex},#{buyUserPhone},#{buyCreateTime},#{buyUpdateTime},#{buyNum},#{buyPrice},#{buyState},#{buyDeliveryIndex})")
    void add(Buy buy);

    @Select("select * from buy where buy_index=#{id}")
    Buy detail(@Param("id") Integer id);

    @Delete("delete from buy where buy_index=#{id}")
    void delete(@Param("id") Integer id);

    @Update("update buy set buy_state=#{state},buy_update_time=now() where buy_index=#{id}")
    void update(@Param("id") Integer id,@Param("state") String state);

    //使用xml配置文件
    List<Buy> list(@Param("myPhone") String myPhone,
                   @Param("isMy") boolean isMy,
                   @Param("userPhone") String userPhone,
                   @Param("goodIndex") String goodIndex,
                   @Param("deliveryIndex") String deliveryIndex,
                   @Param("state") String state,
                   @Param("priceMin") Object priceMin,
                   @Param("priceMax") Object priceMax,
                   @Param("dateMin") Object dateMin,
                   @Param("dateMax") Object dateMax,
                   @Param("numMin") Object numMin,
                   @Param("numMax") Object numMax,
                   @Param("prop") String prop,
                   @Param("order") String order);

    //使用xml配置文件
    List<User> detailUser(Integer id);
    //使用xml配置文件
    List<Delivery> detailDelivery(Integer id);
    //使用xml配置文件
    List<Good> detailGood(Integer id);
}
