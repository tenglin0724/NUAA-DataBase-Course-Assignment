package com.tl.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;

@Data
public class Good {

    //货品图片
//    @URL
    @NotNull
    private String goodPic;

    //货品编号
    @NotNull(groups = Update.class)
    private int goodIndex;

    //货品描述
    @NotNull
    private String goodDescribe;

    //货品价格
    @NotNull
    private double goodPrice;

    //货品数量
    @NotNull
    private int goodNum;

    //货品创建时间
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp goodCreateTime;

    //货品修改时间
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp goodUpdateTime;

    //货品拥有者
    private String goodOwner;

    //创建校验分组
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
