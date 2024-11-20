package com.example.login.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JwtUtilsをBeanに登録
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(secretKey, expirationMs); // シークレットキーと有効期限(ms)
    }
}
