package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.AuthenticateRequest;
import com.hcmut.ssps_server.dto.request.IntrospectRequest;
import com.hcmut.ssps_server.dto.response.AuthenticateResponse;
import com.hcmut.ssps_server.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticateService {
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    public AuthenticateResponse authenticate(AuthenticateRequest request);
}
