package com.tl.backend.controller;

import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.Result;
import com.tl.backend.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodService;

    //创建一个商品
    @PostMapping
    public Result add(@RequestBody @Validated(Good.Add.class) Good good){
        goodService.add(good);
        return Result.success();
    }

    //实现动态分页查询
    @GetMapping
    public Result<PageBean<Good>> list(
            Integer pageNum,
            Integer pageSize,
            //为1表示是用户本人发布的商品
            @RequestParam(required = false) boolean isMy,
            //用于指定拥有者
            @RequestParam(required = false) String userPhone,
            //用于指定关键词
            @RequestParam(required = false) String keyWord,
            //用于指定价格最小值
            @RequestParam(required = false) Object priceMin,
            //用于指定价格最大值
            @RequestParam(required = false) Object priceMax,
            //用于指定日期最小值
            @RequestParam(required = false) Object dateMin,
            //用于指定日期最大值
            @RequestParam(required = false) Object dateMax,
            //用于指定价格最小值
            @RequestParam(required = false) Object numMin,
            //用于指定价格最大值
            @RequestParam(required = false) Object numMax,
            //用于指定排序的列
            @RequestParam(required = false) String prop,
            //用于指定排序的方式
            @RequestParam(required = false) String order
    ){
        PageBean<Good> pageBean = goodService.list(pageNum,pageSize,isMy,userPhone,keyWord,priceMin,priceMax,dateMin,dateMax,numMin,numMax,prop,order);
        return Result.success(pageBean);
    }

    //查询详细数据
    @GetMapping("/detail")
    public Result<Good> detail(@RequestParam Integer id){
        Good good = goodService.detail(id);
        return Result.success(good);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody @Validated(Good.Update.class) Good good){
        goodService.update(good);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        //先找一下
        goodService.delete(id);
        return Result.success();
    }

}
