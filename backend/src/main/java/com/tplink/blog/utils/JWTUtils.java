package com.tplink.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xyd
 * @create 2022-11-03 7:20
 */
public class JWTUtils {

    private static  final  String jwtToken = "123456Mszlu!@$$";

    public static String createToken(Long userId) {
        Map<String, Object> clains = new HashMap<>();
        clains.put("userId", userId);

        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                .setClaims(clains)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {

        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String token = createToken(123456776543123456L);
        System.out.println("================生成的token==============" );
        System.out.println(token);
        System.out.println("================验证token================");
        System.out.println(checkToken(token));

    }
}


