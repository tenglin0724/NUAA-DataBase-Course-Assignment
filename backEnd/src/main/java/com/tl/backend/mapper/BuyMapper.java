package com.tl.backend.mapper;

import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Good;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuyMapper {
    @Insert("insert into buy(buy_good_index,buy_user_phone,buy_create_time,buy_update_time,buy_num,buy_price,buy_state,buy_delivery_index)" +
            "values (#{buyGoodIndex},#{buyUserPhone},#{buyCreateTime},#{buyUpdateTime},#{buyNum},#{buyPrice},#{buyState},#{buyDeliveryIndex})")
    void add(Buy buy);

    //使用xml配置文件
    List<Buy> list(@Param("phone") String phone,@Param("isMy") boolean isMy);

    @Select("select * from buy where buy_index=#{id}")
    Buy detail(@Param("id") Integer id);

    @Delete("delete from buy where buy_index=#{id}")
    void delete(@Param("id") Integer id);

    @Update("update buy set buy_state=#{state},buy_update_time=now() where buy_index=#{id}")
    void update(@Param("id") Integer id,@Param("state") String state);

}
