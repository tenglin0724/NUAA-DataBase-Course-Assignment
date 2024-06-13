package com.tl.backend.interceptors;

import com.tl.backend.util.JwtUtil;
import com.tl.backend.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        //获取令牌token
        String token = request.getHeader("Authorization");
        try {
            //解析JWT令牌
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //将从JWT令牌解析到的内容存入
            ThreadLocalUtil.set(claims);
            //验证成功，放行
            return true;
        }catch (Exception e ){
            //验证失败，拦截
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放ThreadLocal
        ThreadLocalUtil.remove();
    }
}