package com.devitvish.nsestockprice.service.impl;

import java.util.List;

import com.devitvish.nsestockprice.auth.AccessAuthorities;
import com.devitvish.nsestockprice.service.JWTService;

public class JWTServiceImpl implements JWTService {

    @Override
    public boolean isValidateToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    @Override
    public List<AccessAuthorities> extractAuthorities(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractAuthorities'");
    }

}
