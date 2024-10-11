package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.model.user.User;

import java.util.List;

public interface IUserService {
    User createUser(UserCreationRequest request);
    UserResponse getUser(String userId);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(String userId, UserUpdateRequest request);
    void deleteUser(String userId);

}
