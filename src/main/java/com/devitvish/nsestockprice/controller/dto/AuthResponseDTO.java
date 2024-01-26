package com.devitvish.nsestockprice.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AuthResponseDTO {
    private String token;
}
