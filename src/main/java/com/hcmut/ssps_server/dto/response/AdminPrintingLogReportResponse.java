package com.hcmut.ssps_server.dto.response;

import com.hcmut.ssps_server.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPrintingLogReportResponse {
    private int uniqueUserCount;
    private int totalPagesPrinted;
    private Frequency frequency;
    // E.g., "2024-11" for monthly, "Q4-2024" for quarterly, "2024" for yearly
    private String period;
}
