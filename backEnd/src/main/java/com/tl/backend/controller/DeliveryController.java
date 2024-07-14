package com.tl.backend.controller;

import com.tl.backend.pojo.Delivery;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.Result;
import com.tl.backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    //创建一个派送地址
    @PostMapping
    public Result add(@RequestBody @Validated(Delivery.Add.class) Delivery delivery){
        System.out.println(delivery);
        deliveryService.add(delivery);
        return Result.success();
    }

    //获取派送地址列表
    @GetMapping
    public Result<PageBean<Delivery>>list(
            Integer pageNum,
            Integer pageSize,
            //用于判断是否为本人的收货地址
            @RequestParam(required = false) boolean isMy,
            //用于指定所有者电话
            @RequestParam(required = false) String phone,
            //用于指定收货人姓名
            @RequestParam(required = false) String deliveryName,
            //用于指定收货人号码
            @RequestParam(required = false) String deliveryPhone,
            //用于指定地址关键词
            @RequestParam(required = false) String addressKey,
            //用于指定日期最小值
            @RequestParam(required = false) Object createMin,
            //用于指定日期最大值
            @RequestParam(required = false) Object createMax,
            //用于指定排序的列
            @RequestParam(required = false) String prop,
            //用于指定排序的方式
            @RequestParam(required = false) String order
    ){
        PageBean<Delivery> pageBean = deliveryService.list(pageNum,pageSize,isMy,phone,deliveryName,deliveryPhone,addressKey,createMin,createMax,prop,order);
        return Result.success(pageBean);
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
