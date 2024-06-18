package com.tl.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tl.backend.mapper.BuyMapper;
import com.tl.backend.pojo.Buy;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.service.BuyService;
import com.tl.backend.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BuyServiceImpl implements BuyService {

    @Autowired
    private BuyMapper buyMapper;

    @Override
    public void add(Buy buy, Good good) {
        //补充buy实例对象中的数据
        buy.setBuyCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        buy.setBuyUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        buy.setBuyState("未支付");
        Map<String,Object> login = ThreadLocalUtil.get();
        String phone = (String) login.get("phone");
        buy.setBuyUserPhone(phone);

        //根据购买数量计算总价格
        double allPrice = good.getGoodPrice()*buy.getBuyNum();
        buy.setBuyPrice(allPrice);

        //调用mapper接口
        buyMapper.add(buy);
    }

    @Override
    public Buy detail(Integer id) {
        Buy buy = buyMapper.detail(id);
        return buy;
    }


    @Override
    public void delete(Integer id) {
        buyMapper.delete(id);
    }

    @Override
    public void updateState(Integer id, String state) {
        buyMapper.update(id,state);
    }

    @Override
    public PageBean<Buy> list(Integer pageNum, Integer pageSize, boolean isMy, String userPhone, String goodIndex, String deliveryIndex, String state, Object priceMin, Object priceMax, Object dateMin, Object dateMax, Object numMin, Object numMax) {
        //1. 创建PageBean对象
        PageBean<Buy> pb = new PageBean<>();
        //2. 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //3. 调用Mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        String myPhone =(String) map.get("phone");

        List<Buy> goods = buyMapper.list(myPhone,isMy,userPhone,goodIndex,deliveryIndex,state,priceMin,priceMax,dateMin,dateMax,numMin,numMax);
        //page中提供了方法，可以获取PageHelper分页查询获得的总记录条数和对应的数据
        Page<Buy> p =(Page<Buy>)goods;

        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }
}
