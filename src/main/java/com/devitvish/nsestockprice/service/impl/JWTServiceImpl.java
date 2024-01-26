package com.devitvish.nsestockprice.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import com.devitvish.nsestockprice.auth.AccessAuthorities;
import com.devitvish.nsestockprice.config.JWTConfiguration;
import com.devitvish.nsestockprice.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JWTConfiguration jwtConfiguration;
    private final JwtParser jwtParser;

    private Claims extractClaims(String token){
        return jwtParser.parseSignedClaims(token).getPayload();
    }
    
    @Override
    public boolean isTokenExpired(String token) {
        if(!isTokenValid(token)){
            return false;
        }
        final Claims jwtClaims = extractClaims(token);
        final Date expireAt = jwtClaims.getExpiration();
        return expireAt.before(Date.from(Instant.now()));
    }

    @Override
    public boolean isTokenValid(String token) {
        try{
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.info("token {} is invalid error={}", token, e.getMessage());
            return false;
        }
    }

    @Override
    public List<AccessAuthorities> extractRoles(String token) {
        if(!isTokenValid(token)){
            return new ArrayList<>();
        }
        final Claims jwtClaims = extractClaims(token);
        Object access = jwtClaims.get("access");
        if(access instanceof Collection){
            return new ArrayList<>((Collection<AccessAuthorities>) access);
        }
        return new ArrayList<>();
    }

    @Override
    public String generateToken(List<AccessAuthorities> accessAuthorities) {
        final Date expireAt = Date.from(Instant.now().plus(Duration.ofSeconds(jwtConfiguration.getExpiresInSeconds())));
        final Date issuedAt = Date.from(Instant.now());
        return Jwts
                .builder()
                .subject(UUID.randomUUID().toString())
                .claims()
                .add("access", accessAuthorities)
                .expiration(expireAt)
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .and()
                .signWith(jwtConfiguration.getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

}
