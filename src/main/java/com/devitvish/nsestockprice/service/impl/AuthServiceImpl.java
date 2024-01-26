package com.devitvish.nsestockprice.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devitvish.nsestockprice.auth.AccessAuthorities;
import com.devitvish.nsestockprice.controller.dto.AuthResponseDTO;
import com.devitvish.nsestockprice.service.AuthService;
import com.devitvish.nsestockprice.service.JWTService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JWTService jwtService;

    @Override
    public AuthResponseDTO createBasicAccessToken() {
        final List<AccessAuthorities> roles = Arrays.asList(AccessAuthorities.GET_QUOTE,
                AccessAuthorities.GET_HISTORICAL_PRICE);
        final String token = jwtService.generateToken(roles);
        return AuthResponseDTO
                .builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDTO createRealtimeAccessToken() {
        final List<AccessAuthorities> roles = Arrays.asList(AccessAuthorities.GET_QUOTE,
                AccessAuthorities.GET_HISTORICAL_PRICE, AccessAuthorities.LIVE_PRICE);
        final String token = jwtService.generateToken(roles);
        return AuthResponseDTO
                .builder()
                .token(token)
                .build();
    }

}
