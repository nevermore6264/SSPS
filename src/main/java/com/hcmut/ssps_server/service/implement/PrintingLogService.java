package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.dto.response.AdminPrintingLogReportResponse;
import com.hcmut.ssps_server.dto.response.AdminPrintingLogResponse;
import com.hcmut.ssps_server.enums.Frequency;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrintingLogService implements IPrintingLogService {
    private PrintingLogRepository printingLogRepository;

    private StudentRepository studentRepository;

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

    /**
     * Func: viewAllPrintLog
     *
     * @param startDate LocalDate
     * @param endDate   LocalDate
     * @return List<AdminPrintingLogResponse>
     */
    @Override
    public List<AdminPrintingLogResponse> viewAllPrintLog(LocalDate startDate, LocalDate endDate) {
        return printingLogRepository.viewAllPrintLog(startDate, endDate);
    }

    /**
     * Func: viewPrintLog
     *
     * @param printingLogId Long
     * @return AdminPrintingLogResponse
     */
    @Override
    public AdminPrintingLogResponse viewPrintLog(Long printingLogId) {
        return printingLogRepository.viewPrintLog(printingLogId);
    }

    @Override
    public AdminPrintingLogReportResponse generateUsageReports(Frequency frequency) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        int currentQuarter = (endDate.getMonthValue() - 1) / 3 + 1;  // Define `currentQuarter` here

        // Define start date based on frequency
        switch (frequency) {
            case MONTHLY:
                startDate = endDate.with(TemporalAdjusters.firstDayOfMonth());
                break;
            case QUARTERLY:
                startDate = endDate.withMonth((currentQuarter - 1) * 3 + 1)
                        .with(TemporalAdjusters.firstDayOfMonth());
                break;
            case YEARLY:
                startDate = endDate.with(TemporalAdjusters.firstDayOfYear());
                break;
            default:
                throw new IllegalArgumentException("Unsupported frequency: " + frequency);
        }

        // Fetch data within the date range
        List<Object[]> logs = printingLogRepository.findLogsByDateRange(startDate, endDate);

        // Calculate unique users and total page count
        Set<Long> uniqueUsers = new HashSet<>();
        int totalPagesPrinted = 0;

        for (Object[] log : logs) {
            uniqueUsers.add((Long) log[0]); // Assuming log[0] is student_id
            totalPagesPrinted += (Integer) log[1]; // Assuming log[1] is page_count
        }

        // Prepare response
        AdminPrintingLogReportResponse response = new AdminPrintingLogReportResponse();
        response.setUniqueUserCount(uniqueUsers.size());
        response.setTotalPagesPrinted(totalPagesPrinted);
        response.setFrequency(frequency);

        // Set the `period` based on frequency
        String period = switch (frequency) {
            case MONTHLY -> endDate.getYear() + "-" + endDate.getMonthValue();
            case QUARTERLY -> "Q" + currentQuarter + "-" + endDate.getYear();
            case YEARLY -> String.valueOf(endDate.getYear());
        };

        response.setPeriod(period);

        return response;
    }
}
