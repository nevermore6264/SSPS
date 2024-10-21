package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.service.interf.IAdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminService implements IAdminService {
}
