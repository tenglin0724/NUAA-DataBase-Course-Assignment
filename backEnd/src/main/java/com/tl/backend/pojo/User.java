package com.tl.backend.pojo;


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
    private Date birthday;
    @NotEmpty
    private String userAddress;
    @NotNull
    private String phone;
    @JsonIgnore
    private String password;
    private Timestamp userCreateTime ;
    private Timestamp userUpdateTime;
}
