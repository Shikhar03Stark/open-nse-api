package com.devitvish.nsestockprice.service;

import java.util.List;

import com.devitvish.nsestockprice.auth.AccessAuthorities;

public interface JWTService {

    boolean isTokenExpired(String token);
    boolean isTokenValid(String token);
    List<AccessAuthorities> extractRoles(String token);
    String generateToken(List<AccessAuthorities> accessAuthorities);

}
