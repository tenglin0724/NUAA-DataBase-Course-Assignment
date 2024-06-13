package com.tl.backend.controller;

import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.Result;
import com.tl.backend.service.BuyService;
import com.tl.backend.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;

    @Autowired
    private GoodService goodService;

    //TODO:新增一个购买记录
    @PostMapping
    public Result add(@RequestBody @Validated Buy buy){
        //调用商品的函数，查询商品数量是否符合当前要求的购买数量
        Good good = goodService.detail(buy.getBuyGoodIndex());
        int goodNum=good.getGoodNum();
        if(goodNum<=0){
            return Result.error("商品已售馨");
        }
        if(goodNum< buy.getBuyNum()){
            return Result.error("余货不足");
        }
        buyService.add(buy,good);
        //完成添加后，再调用函数使得该商品总数量减去对应值
        good.setGoodNum(goodNum-buy.getBuyNum());
        goodService.update(good);
        return Result.success();
    }

    //TODO:查看所有的购买记录（分页动态查询）
    @GetMapping
    public Result<PageBean<Buy>>list(
            Integer pageNum,
            Integer pageSize,
            //用于判断是否为本人的购买记录
            @RequestParam(required = false) boolean isMy
    ){
        PageBean<Buy> pageBean = buyService.list(pageNum,pageSize,isMy);
        return Result.success(pageBean);
    }

    //TODO:查看某一个详细的购买记录
    @GetMapping("/detail")
    public Result<Buy> detail(@RequestParam Integer id){
        Buy buy = buyService.detail(id);
        return Result.success(buy);
    }
    //TODO:删除一条购买记录（只有状态为已完成或已退货的才能被删除）
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        //判断状态是否为已完成或已退货
        Buy detail = buyService.detail(id);
        if(!detail.getBuyState().equals("已完成")
        && !detail.getBuyState().equals("已退货")){
            return Result.error("未完成的订单信息不可删除");
        }
        //可以删除的
        buyService.delete(id);
        return Result.success();
    }

    //TODO:更新购买记录信息（只能修改状态）
    @PutMapping
    public Result updateState(@RequestParam Integer id,@RequestParam String state){
        buyService.updateState(id,state);
        return Result.success();
    }

}
