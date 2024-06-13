package com.tl.backend.controller;

import com.tl.backend.pojo.Delivery;
import com.tl.backend.pojo.Result;
import com.tl.backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    //创建一个派送地址
    @PostMapping
    public Result add(@RequestBody @Validated(Delivery.Add.class) Delivery delivery){
        deliveryService.add(delivery);
        return Result.success();
    }

    //获取派送地址列表
    @GetMapping
    public Result<List<Delivery>>list(){
        List<Delivery> deliveries = deliveryService.list();
        return Result.success(deliveries);
    }

    //获取详细的地址信息
    @GetMapping("/detail")
    public Result<Delivery>detail(@RequestParam Integer id){
        Delivery delivery = deliveryService.detail(id);
        return Result.success(delivery);
    }

    //修改地址信息
    @PutMapping
    public Result update(@RequestBody @Validated(Delivery.Update.class) Delivery delivery){
        deliveryService.update(delivery);
        return Result.success();
    }

    //删除地址信息
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        deliveryService.delete(id);
        return Result.success();
    }



}
