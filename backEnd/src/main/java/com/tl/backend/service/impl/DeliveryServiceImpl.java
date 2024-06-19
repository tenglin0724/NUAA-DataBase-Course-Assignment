package com.tl.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tl.backend.mapper.DeliveryMapper;
import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Delivery;
import com.tl.backend.pojo.PageBean;
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

    @Override
    public PageBean<Delivery> list(Integer pageNum, Integer pageSize, boolean isMy, String phone, String deliveryName, String deliveryPhone, String addressKey, Object createMin, Object createMax) {
        //1. 创建PageBean对象
        PageBean<Delivery> pb = new PageBean<>();
        //2. 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //3. 调用Mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        String myPhone =(String) map.get("phone");

        List<Delivery> goods = deliveryMapper.list(myPhone,isMy,phone,deliveryName,deliveryPhone,addressKey,createMin,createMax);
        //page中提供了方法，可以获取PageHelper分页查询获得的总记录条数和对应的数据
        Page<Delivery> p =(Page<Delivery>)goods;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }


}
