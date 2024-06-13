package com.tl.backend.service.impl;

import com.tl.backend.mapper.DeliveryMapper;
import com.tl.backend.pojo.Delivery;
import com.tl.backend.service.DeliveryService;
import com.tl.backend.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public void add(Delivery delivery) {
        //补充delivery的信息
        delivery.setDeliveryCreateTime(String.valueOf(LocalDateTime.now()));

        Map<String,Object> login = ThreadLocalUtil.get();
        String phone = (String) login.get("phone");
        delivery.setDeliveryOwner(phone);
        //调用mapper层接口
        deliveryMapper.add(delivery);
    }

    @Override
    public List<Delivery> list() {
        //获取用户主键
        Map<String,Object> login = ThreadLocalUtil.get();
        String phone = (String) login.get("phone");
        //调用mapper层接口
        List<Delivery> deliveries = deliveryMapper.list(phone);
        return deliveries;
    }

    @Override
    public Delivery detail(Integer id) {
        Delivery delivery = deliveryMapper.detail(id);
        return delivery;
    }

    @Override
    public void update(Delivery delivery) {
        //补充信息
        delivery.setDeliveryCreateTime(String.valueOf(LocalDateTime.now()));
        deliveryMapper.update(delivery);
    }

    @Override
    public void delete(Integer id) {
        //删除地址信息
        deliveryMapper.delete(id);
    }


}
