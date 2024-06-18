package com.tl.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Delivery {
    //派送地址序号
    @NotNull(groups = Update.class)
    private int deliveryIndex;

    //派送地址姓名
    @NotEmpty
    private String deliveryName;

    //派送地址的地区
    @NotEmpty
    private String deliveryArea;

    //派送的详细地址
    @NotEmpty
    private String deliveryFullAddress;

    //派送的电话
    @NotEmpty
    private String deliveryPhone;

    //派送地址的创建地址
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private String deliveryCreateTime;

    //派送的拥有者
    private String deliveryOwner;


    //创建校验分组
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
