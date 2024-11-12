package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.response.AdminPrintingLogResponse;
import com.hcmut.ssps_server.model.Printing;

import java.time.LocalDate;
import java.util.List;

public interface IPrintingLogService {
    void addPrintingLog(Printing printing);

    List<AdminPrintingLogResponse> viewAllPrintLog(LocalDate startDate, LocalDate endDate);

    AdminPrintingLogResponse viewPrintLog(Long printingLogId);
}
