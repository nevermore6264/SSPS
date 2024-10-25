package com.hcmut.ssps_server.controller;

import com.hcmut.ssps_server.dto.request.AuthenticateRequest;
import com.hcmut.ssps_server.dto.request.IntrospectRequest;
import com.hcmut.ssps_server.dto.response.ApiResponse;
import com.hcmut.ssps_server.dto.response.AuthenticateResponse;
import com.hcmut.ssps_server.dto.response.IntrospectResponse;
import com.hcmut.ssps_server.service.interf.IAuthenticateService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final IAuthenticateService authenticateService;

    @PostMapping("/login")
    public ApiResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request) {
        return ApiResponse.<AuthenticateResponse>builder()
                .result(authenticateService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticateService.introspect(request))
                .build();
    }

}
