package com.tl.backend.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class User {
    private String username;
    private String userPic;
    @NotNull
    private String sex;
    @NotNull
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date birthday;
    @NotEmpty
    private String userAddress;
    @NotNull
    private String phone;
    @JsonIgnore
    private String password;

    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp userCreateTime ;
    @JsonFormat(pattern = "YYYY-MM-dd hh:mm:ss")
    private Timestamp userUpdateTime;
}
