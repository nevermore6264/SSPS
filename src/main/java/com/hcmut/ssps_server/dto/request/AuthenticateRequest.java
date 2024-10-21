package com.hcmut.ssps_server.dto.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthenticateRequest {
    String username;
    String password;
}
