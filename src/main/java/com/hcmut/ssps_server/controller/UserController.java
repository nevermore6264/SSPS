package com.hcmut.ssps_server.controller;

import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.response.ApiResponse;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.service.interf.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserController {
    IUserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<User>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
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

}
