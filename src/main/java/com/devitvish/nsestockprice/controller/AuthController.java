package com.devitvish.nsestockprice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devitvish.nsestockprice.controller.dto.AuthResponseDTO;
import com.devitvish.nsestockprice.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping(path = "/auth/basic")
    public HttpEntity<AuthResponseDTO> getBasicRoleToken(){
        final AuthResponseDTO responseDTO = authService.createBasicAccessToken();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(path = "/auth/live")
    public HttpEntity<AuthResponseDTO> getLiveRoleToken(){
        final AuthResponseDTO responseDTO = authService.createRealtimeAccessToken();
        return ResponseEntity.ok().body(responseDTO);
    }

}
