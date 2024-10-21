package com.hcmut.ssps_server.controller;

import com.hcmut.ssps_server.dto.request.StudentCreationRequest;
import com.hcmut.ssps_server.dto.response.ApiResponse;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.model.user.Student;
import com.hcmut.ssps_server.service.interf.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class StudentController {
    IStudentService studentService;

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody @Valid StudentCreationRequest request) {
        return ApiResponse.<Student>builder()
                .result(studentService.createStudent(request))
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<StudentResponse> getMyInfo() {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.getMyInfo())
                .build();
    }
}
