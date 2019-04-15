package com.tensquare.user.interceptor;

import com.tensquare.user.util.MyJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;
import io.jsonwebtoken.Claims ;

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
    MyJwtUtil myJwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)){
            if (header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    Claims claims = myJwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null || roles.equals("admin")){
                        request.setAttribute("claims_admin",token);
                    }
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
