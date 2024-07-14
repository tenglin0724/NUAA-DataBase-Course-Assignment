package com.tl.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tl.backend.mapper.GoodMapper;
import com.tl.backend.pojo.Good;
import com.tl.backend.pojo.PageBean;
import com.tl.backend.service.GoodService;
import com.tl.backend.util.CamelToUnderlineUtil;
import org.apache.commons.lang3.StringUtils;
import com.tl.backend.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public void add(Good good) {
        //补充相关信息
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone =(String) map.get("phone");
        good.setGoodOwner(phone);
        good.setGoodCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        good.setGoodUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        //调用mapper接口函数
        goodMapper.add(good);
    }

    @Override
    public void update(Good good) {
        //补充修改时间
        good.setGoodUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        goodMapper.update(good);
    }

    @Override
    public void delete(Integer id) {
        //根据id删除商品
        goodMapper.delete(id);
    }

    @Override
    public Good detail(Integer id) {
        Good good = goodMapper.detail(id);
        return good;
    }

    @Override
    public PageBean<Good> list(Integer pageNum, Integer pageSize, boolean isMy, String userPhone, String keyWord, Object priceMin, Object priceMax, Object dateMin, Object dateMax, Object numMin, Object numMax,String prop,String order) {
        //判断prop是否为空
        if(prop!=null){
            prop= CamelToUnderlineUtil.camel2under(prop);
        }
        //1. 创建PageBean对象
        PageBean<Good> pb = new PageBean<>();
        //2. 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //3. 调用Mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        String myPhone =(String) map.get("phone");

        List<Good> goods = goodMapper.list(myPhone,isMy,userPhone,keyWord,priceMin,priceMax,dateMin,dateMax,numMin,numMax,prop,order);
        //page中提供了方法，可以获取PageHelper分页查询获得的总记录条数和对应的数据
        Page<Good> p =(Page<Good>)goods;


        //把数据填充到PageBean中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }
}
