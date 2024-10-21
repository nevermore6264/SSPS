package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.model.user.User;

import java.util.List;

public interface IUserService {
    User createUser(UserCreationRequest request);
    UserResponse getUser(Long userId);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);

}
