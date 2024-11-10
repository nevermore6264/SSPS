package com.hcmut.ssps_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PrintingLogResponse {
    private Integer logId;
    private String documentName;
    private Integer pagesPrinted;
}