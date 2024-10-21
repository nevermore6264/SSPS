package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.StudentCreationRequest;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.model.user.Student;

public interface IStudentService {
    Student createStudent(StudentCreationRequest request);
    StudentResponse getMyInfo();
}
