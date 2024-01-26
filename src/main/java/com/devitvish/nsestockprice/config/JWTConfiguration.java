package com.devitvish.nsestockprice.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Configuration
@RequiredArgsConstructor
public class JWTConfiguration {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expire.seconds:86400}")
    private Integer expiresInSeconds;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Bean
    public JwtParser jwtParser(){
        return Jwts.parser().verifyWith(getSecretKey()).build();
    }


}
