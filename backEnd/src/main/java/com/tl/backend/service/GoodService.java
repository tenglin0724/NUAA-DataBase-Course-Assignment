package com.tl.backend.service;

import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;

import java.util.Map;

public interface GoodService {
    //新增商品
    void add(Good good);

    //实现货品修改
    void update(Good good);

    //实现删除货品
    void delete(Integer id);

    //查询详细的商品信息
    Good detail(Integer id);

    //实现条件分页查询
    PageBean<Good> list(Integer pageNum, Integer pageSize, boolean isMy, String userPhone, String keyWord, Object priceMin, Object priceMax, Object dateMin, Object dateMax, Object numMin, Object numMax,String prop,String order);
}
