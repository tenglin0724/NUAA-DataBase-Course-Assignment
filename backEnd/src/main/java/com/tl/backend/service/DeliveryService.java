package com.tl.backend.service;

import com.tl.backend.pojo.Delivery;

import java.util.List;

public interface DeliveryService {
    //添加
    void add(Delivery delivery);

    //获取所有列表
    List<Delivery> list();

    //获取详细信息
    Delivery detail(Integer id);

    //修改地址信息
    void update(Delivery delivery);

    //删除地址信息
    void delete(Integer id);
}
