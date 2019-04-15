package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/11 11:36
 * @Description: tensquare_parent
 */
public class ParseJwt {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_lvLoiLCJpYXQiOjE1NTQ5NjYwNzUsImV4cCI6MTU1NDk2NjEzNSwicm9sZSI6ImFkbWluIn0.J-6VhQD2gxT3cJMwiDfno3yM8Gzf_A7BxFOOG4fOwqs")
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()) );
        System.out.println(claims.get("role"));

    }

}
