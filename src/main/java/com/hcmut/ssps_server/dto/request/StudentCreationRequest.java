package com.hcmut.ssps_server.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {
    @Size(min = 5, message = "EMAIL_INVALID")
    String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String fullName;

    @Size(min = 7, max = 7, message = "STUDENT_ID_INVALID")
    String studentId;
}
