package com.hcmut.ssps_server.dto.request;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UploadConfigRequest {
    int printerId;
    String paperSize;
    String sidedType;
    int numberOfCopies;
}
