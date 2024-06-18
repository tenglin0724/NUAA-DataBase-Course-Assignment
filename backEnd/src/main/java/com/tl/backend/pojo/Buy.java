package com.tl.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Buy {
    //购买记录编号
    private int buyIndex;

    //购买的商品编号
    @NotNull
    private int buyGoodIndex;

    //购买者的主键
    private String buyUserPhone;

    //购买时间
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp buyCreateTime;

    //更新时间
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp buyUpdateTime;

    //购买数量
    @NotNull
    private int buyNum;

    //购买总价格
    private double buyPrice;

    //购买的状态

    private String buyState;

    //购买的派送地址
    @NotNull
    private int buyDeliveryIndex;
}
