package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.enums.PrintableStatus;
import com.hcmut.ssps_server.model.Printer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPrinterService {
    void print(int printerId);
    PrintableStatus isPrintable(Printer printer, MultipartFile file) throws IOException;
}
