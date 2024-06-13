package com.tl.backend.service;

import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;

public interface BuyService {
    //添加一个购买记录
    void add(Buy buy, Good good);

    //获取所有购买记录列表
    PageBean<Buy> list(Integer pageNum, Integer pageSize, boolean isMy);

    //获取详细的购买记录
    Buy detail(Integer id);

    //删除订单信息
    void delete(Integer id);

    //更新状态
    void updateState(Integer id, String state);
}
