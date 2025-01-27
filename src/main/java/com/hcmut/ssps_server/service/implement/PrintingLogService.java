package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.exception.AppException;
import com.hcmut.ssps_server.exception.ErrorCode;
import com.hcmut.ssps_server.model.Printing;
import com.hcmut.ssps_server.model.PrintingLog;
import com.hcmut.ssps_server.repository.PrintingLogRepository;
import com.hcmut.ssps_server.repository.UserRepository.StudentRepository;
import com.hcmut.ssps_server.service.interf.IPrintingLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrintingLogService implements IPrintingLogService {
    PrintingLogRepository printingLogRepository;
    StudentRepository studentRepository;
    @Override
    public void addPrintingLog(Printing printing) {
        PrintingLog printingLog = new PrintingLog();
        printingLog.setDocument(printing.getDocument());
        printingLog.setAdminPrintMail(printing.getAdminPrintMail());
        printingLog.setPrintingStartTime(printing.getPrintingStartTime());
        printingLog.setPrintingEndTime(LocalDateTime.now());
        printingLog.setStudent(studentRepository.findByUser_Email(printing.getStudentUploadMail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        printingLogRepository.save(printingLog);
    }
}
