package com.hcmut.ssps_server.controller;

import com.hcmut.ssps_server.dto.request.PrinterCreationRequest;
import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.response.AdminPrintingLogReportResponse;
import com.hcmut.ssps_server.dto.response.AdminPrintingLogResponse;
import com.hcmut.ssps_server.dto.response.ApiResponse;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.enums.Frequency;
import com.hcmut.ssps_server.model.Printer;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.service.interf.IAdminService;
import com.hcmut.ssps_server.service.interf.IPrinterService;
import com.hcmut.ssps_server.service.interf.IPrintingLogService;
import com.hcmut.ssps_server.service.interf.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AdminController {
    private IAdminService adminService;

    private IUserService userService;

    private IPrinterService printerService;

    private IPrintingLogService printingLogService;

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
    ApiResponse<List<StudentResponse>> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<List<StudentResponse>>builder()
                .result(adminService.getAllStudents(pageable))
                .build();
    }

    @PutMapping("/update-user/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/delete-user/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User deleted")
                .build();
    }

    @PostMapping("/print/{printerId}")
    ApiResponse<String> print(@PathVariable("printerId") int printerId) {
        printerService.print(printerId);
        return ApiResponse.<String>builder()
                .result("Printer " + printerId + " printed successfully")
                .build();
    }

    @PostMapping("/add-printer")
    ApiResponse<Printer> addPrinter(@RequestBody @Valid PrinterCreationRequest request) {
        return ApiResponse.<Printer>builder()
                .result(printerService.addPrinter(request))
                .build();
    }

    @PatchMapping("/update-printer/{printerId}")
    ApiResponse<Printer> updatePrinter(@PathVariable Long printerId, @RequestBody Map<String, Object> updates) {
        return ApiResponse.<Printer>builder()
                .result(printerService.updatePrinter(printerId, updates))
                .build();
    }

    @GetMapping("/get-printer/{printerId}")
    ApiResponse<Printer> getPrinter(@PathVariable Long printerId) {
        return ApiResponse.<Printer>builder()
                .result(printerService.getPrinter(printerId))
                .build();
    }

    @GetMapping("/get-all-printers")
    ApiResponse<List<Printer>> getAllPrinters(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<List<Printer>>builder()
                .result(printerService.getAllPrinters(pageable))
                .build();
    }

    @DeleteMapping("/delete-printer/{printerId}")
    ApiResponse<String> deletePrinter(@PathVariable Long printerId) {
        printerService.deletePrinter(printerId);
        return ApiResponse.<String>builder()
                .result("Printer deleted successfully")
                .build();
    }

    @PutMapping("/enable-printer/{printerId}")
    ApiResponse<String> enablePrinter(@PathVariable Long printerId) {
        printerService.enablePrinter(printerId);
        return ApiResponse.<String>builder()
                .result("Printer enabled successfully")
                .build();
    }

    @PutMapping("/disable-printer/{printerId}")
    ApiResponse<String> disablePrinter(@PathVariable Long printerId) {
        printerService.disablePrinter(printerId);
        return ApiResponse.<String>builder()
                .result("Printer disabled successfully")
                .build();
    }

    /**
     * API cho phép SPSO xem toàn bộ nhật ký của các hoạt động in ấn, có thể bao gồm thông tin chi tiết như ngày giờ,
     * số trang in và thông tin người dùng.
     *
     * @param startDate Ngày bắt đầu tìm kiếm
     * @param endDate   Ngày kết thúc tìm kiếm
     * @return Danh sách printing_log join với document join printing join student join user
     */
    @GetMapping("/view-print-logs")
    ApiResponse<Page<AdminPrintingLogResponse>> viewAllPrintLog(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<AdminPrintingLogResponse>>builder()
                .result(printingLogService.viewAllPrintLog(startDate, endDate, pageable))
                .build();
    }

    /**
     * API cho phép SPSO xem chi tiết nhật ký của các hoạt động in ấn, có thể bao gồm thông tin chi tiết như ngày giờ,
     * số trang in và thông tin người dùng.
     *
     * @param printingLogId Id của bản ghi printing Log Id
     * @return Thông tin printing_log join với document join printing join student join user
     */
    @GetMapping("/view-print-log/{printingLogId}")
    ApiResponse viewPrintLog(
            @PathVariable Optional<Long> printingLogId
    ) {
        if (printingLogId.isEmpty()) {
            return ApiResponse.builder()
                    .result("PrintingLogId is required in the URL path")
                    .build();
        }
        return ApiResponse.<AdminPrintingLogResponse>builder()
                .result(printingLogService.viewPrintLog(printingLogId.get()))
                .build();
    }

    @GetMapping("/generate-usage-reports")
    public ApiResponse generateUsageReports(
            @RequestParam(required = false) Frequency frequency
    ) {
        if (frequency == null) {
            return ApiResponse.builder()
                    .result("Frequency parameter is required")
                    .build();
        }
        return ApiResponse.<AdminPrintingLogReportResponse>builder()
                .result(printingLogService.generateUsageReports(frequency))
                .build();
    }
}
