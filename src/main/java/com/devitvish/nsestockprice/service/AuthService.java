package com.devitvish.nsestockprice.service;

import com.devitvish.nsestockprice.controller.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO createBasicAccessToken();
    AuthResponseDTO createRealtimeAccessToken();
}
