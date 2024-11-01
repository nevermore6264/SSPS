package com.hcmut.ssps_server.mapper;

import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.model.user.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse toStudentResponse(Student student) {
        if (student == null) {
            return null;
        } else {
            StudentResponse.StudentResponseBuilder studentResponse = StudentResponse.builder();
            studentResponse.fullName(student.getUser().getFullName());
            studentResponse.email(student.getUser().getEmail());
            studentResponse.studentId(student.getStudentId());
            studentResponse.numOfPages(student.getNumOfPages());
            return studentResponse.build();
        }
    }
}
