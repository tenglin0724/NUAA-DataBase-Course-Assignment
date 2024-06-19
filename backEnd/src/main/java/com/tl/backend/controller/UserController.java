package com.tl.backend.controller;

import com.tl.backend.pojo.PageBean;
import com.tl.backend.pojo.Result;
import com.tl.backend.pojo.User;
import com.tl.backend.service.UserService;
import com.tl.backend.util.JwtUtil;
import com.tl.backend.util.Md5Util;
import com.tl.backend.util.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @PostMapping("/register")
    public Result register(String username,
                           @Pattern(regexp = "^[\\d\\*]{7,15}$", message = "联系电话输入有误") String phone,
                           @Pattern(regexp = "^(?=.*[a-zA-Z0-9].*)(?=.*[a-zA-Z.!@#$%^&*].*)(?=.*[0-9.!@#$%^&*].*).{6,32}$", message = "密码至少包含数字，字母和符号的两种") String password){
        //判断是否已经注册
        User u = userService.findByPhone(phone);

        if(u==null){
            //没注册过
            //注册
            userService.register(username,phone,password);
            return Result.success();
        }else{
            //已经注册过
            return Result.error("用户已存在");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String>login(@Pattern(regexp = "^[\\d\\*]{7,15}$", message = "联系电话输入有误") String phone,
                               @Pattern(regexp = "^(?=.*[a-zA-Z0-9].*)(?=.*[a-zA-Z.!@#$%^&*].*)(?=.*[0-9.!@#$%^&*].*).{6,32}$", message = "密码至少包含数字，字母和符号的两种") String password){
        //判断是否已经注册
        User u = userService.findByPhone(phone);

        if(u==null){
            return Result.error("用户不存在");
        }
        //校验密码是否正确
        if(Md5Util.getMD5String(password).equals(u.getPassword())){
            //生成JWT令牌
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("phone",u.getPhone());
            claims.put("username",u.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }else {
            //密码错误
            return Result.error("密码错误");
        }
    }

    //获取用户详细信息
    //从请求头中的JWT令牌中获取信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token){
        //使用get方法获取
        Map<String,Object> u=ThreadLocalUtil.get();
        //强转成字符串
        String phone = (String)u.get("phone");
        //根据用户的phone获取用户
        User byPhone = userService.findByPhone(phone);
        //返回结果
        return Result.success(byPhone);
    }

    //用户信息更新
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    //用户更新头像
    @PatchMapping("/updatePic")
    public Result updatePic(@RequestParam @URL String picUrl){
        userService.updatePic(picUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String>params){
        //获取各部分参数并进行校验
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String ack_pwd = params.get("ack_pwd");

        //判断是否存在
        if(!StringUtils.hasLength(old_pwd)||!StringUtils.hasLength(new_pwd)||!StringUtils.hasLength(ack_pwd)){
            return Result.error("缺少必要参数");
        }

        //取得原数据
        Map<String,Object> map = ThreadLocalUtil.get();
        String phone = (String) map.get("phone");
        User byPhone = userService.findByPhone(phone);

        System.out.println(Md5Util.checkPassword(byPhone.getPassword(),old_pwd));
        //判断拿到的密码是否和输入的一致
        if(!byPhone.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("原密码不正确");
        }

        //判断两次输入的密码是否一样
        if(!new_pwd.equals(ack_pwd)){
            return Result.error("两次填写的新密码不一样");
        }

        //校验完成
        userService.updatePwd(new_pwd);
        return Result.success();
    }

    //获取用户列表
    @GetMapping
    public Result<PageBean<User>> list(
            Integer pageNum,
            Integer pageSize,
            //用于指定电话号码
            @RequestParam(required = false) String phone,
            //用于指定姓名
            @RequestParam(required = false) String userName,
            //用于指定性别
            @RequestParam(required = false) String sex,
            //用于指定常驻地址
            @RequestParam(required = false) String address,
            //用于指定出生最早日期
            @RequestParam(required = false) Object brithMin,
            //用于指定出生最晚日期
            @RequestParam(required = false) Object brithMax,
            //用于指定创建最早日期
            @RequestParam(required = false) Object createMin,
            //用于指定创建最晚日期
            @RequestParam(required = false) Object createMax
    ){
        PageBean<User> pageBean=userService.list(pageNum,pageSize,phone,userName,sex,address,brithMin,brithMax,createMin,createMax);
        return Result.success(pageBean);
    }

    //删除用户
    @DeleteMapping
    public Result delete(@RequestParam String phone){
        //判断是否为指定的管理员
        if(phone.equals("19103878295")){
            return Result.error("管理员用户不可删除");
        }else{
            userService.delete(phone);
            return Result.success();
        }
    }
}
