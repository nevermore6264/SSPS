package com.hcmut.ssps_server.controller;

import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.request.PrinterCreationRequest;
import com.hcmut.ssps_server.dto.response.ApiResponse;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.model.Printer;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.service.interf.IAdminService;
import com.hcmut.ssps_server.service.interf.IPrinterService;
import com.hcmut.ssps_server.service.interf.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AdminController {
    IAdminService adminService;
    IUserService userService;
    IPrinterService printerService;

    @PostMapping("/register")
    ApiResponse<User> createAdmin(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<User>builder()
                .result(adminService.createAdmin(request))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(adminService.getMyInfo())
                .build();
    }

    @GetMapping("/get-student/{studentId}")
    ApiResponse<StudentResponse> getUser(@PathVariable("studentId") Long studentId) {
        return ApiResponse.<StudentResponse>builder()
                .result(adminService.getStudent(studentId))
                .build();
    }

    @GetMapping("/get-all-students")
    ApiResponse<List<StudentResponse>> getAllStudents() {
        return ApiResponse.<List<StudentResponse>>builder()
                .result(adminService.getAllStudents())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest request ) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User deleted")
                .build();
    }

    @GetMapping("/print/{printerId}")
    ApiResponse<String> print(@PathVariable("printerId") int printerId) {
        printerService.print(printerId);
        return ApiResponse.<String>builder()
                .result("Printer " + printerId + " printed successfully")
                .build();

    }
    @PostMapping("/adds-printer")
    public ApiResponse<Printer> addPrinter(@RequestBody @Valid PrinterCreationRequest request) {
        return ApiResponse.<Printer>builder()
                .result(printerService.addPrinter(request))
                .build();
    }


}
