package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.enums.PrintableStatus;
import org.springframework.web.multipart.MultipartFile;

public interface IPrinterService {
    PrintableStatus isPrintable(int printerId, MultipartFile file);
}
