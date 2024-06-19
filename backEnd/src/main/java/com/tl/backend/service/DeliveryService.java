package com.tl.backend.service;

import com.tl.backend.pojo.Delivery;
import com.tl.backend.pojo.PageBean;

import java.util.List;

public interface DeliveryService {
    //添加
    void add(Delivery delivery);


//    PageBean<Delivery> list(Integer pageNum,Integer pageSize);

    //获取详细信息
    Delivery detail(Integer id);

    //修改地址信息
    void update(Delivery delivery);

    //删除地址信息
    void delete(Integer id);

    //获取所有列表
    PageBean<Delivery> list(Integer pageNum, Integer pageSize, boolean isMy, String phone, String deliveryName, String deliveryPhone, String addressKey, Object createMin, Object createMax);
}
