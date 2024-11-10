package com.hcmut.ssps_server.dto.request;

import com.hcmut.ssps_server.enums.PrinterStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrinterCreationRequest {

    @Size(min = 1, message = "Printer location cannot be empty")
    String printerLocation;

    PrinterStatus status;

    @Min(value = 0, message = "Papers left must be at least 0")
    int papersLeft;

}
