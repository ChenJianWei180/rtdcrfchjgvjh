package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/11 18:40
 * @Description: tensquare_parent
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取头信息
       String header = request.getHeader("Authorization");
       //判断头信息是否为空
        if (header!=null && !"".equals(header)){
            //判断头信息是否是Bearer+" "开头的
            if (header.startsWith("Bearer ")){
                //从第七位截取获取token
                String token = header.substring(7);

                try {
                    //解析token
                    Claims claims = jwtUtil.parseJWT(token);
                    //获取token角色
                    String roles = (String) claims.get("roles");
                    //如角色为admin则为管理员
                    if (roles != null || roles.equals("admin")){
                        request.setAttribute("claims_admin",token);
                    }
                    //如角色为user则为普通用户
                    if (roles != null || roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌有误");
                }
            }
        }

        return true;
    }
}
