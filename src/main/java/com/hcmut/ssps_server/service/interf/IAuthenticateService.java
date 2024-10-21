package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.AuthenticateRequest;
import com.hcmut.ssps_server.dto.response.AuthenticateResponse;

public interface IAuthenticateService {
    public AuthenticateResponse authenticate(AuthenticateRequest request);
}
