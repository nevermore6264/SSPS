package com.hcmut.ssps_server.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthenticateResponse {
    boolean authenticated;
    String token;
}
