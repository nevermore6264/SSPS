package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.PrinterCreationRequest;
import com.hcmut.ssps_server.dto.request.UploadConfigRequest;
import com.hcmut.ssps_server.enums.PrintableStatus;
import com.hcmut.ssps_server.model.Printer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPrinterService {
    Printer addPrinter(PrinterCreationRequest request);
    void deletePrinter(Long printerId);
    void enablePrinter(Long printerId);
    void disablePrinter(Long printerId);
    void print(int printerId);
    PrintableStatus isPrintable(MultipartFile file, UploadConfigRequest uploadConfigRequest) throws IOException;
}
