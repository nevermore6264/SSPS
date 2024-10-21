package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.dto.request.UserCreationRequest;
import com.hcmut.ssps_server.dto.request.UserUpdateRequest;
import com.hcmut.ssps_server.dto.response.UserResponse;
import com.hcmut.ssps_server.exception.AppException;
import com.hcmut.ssps_server.exception.ErrorCode;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.mapper.UserMapper;
import com.hcmut.ssps_server.repository.UserRepository;
import com.hcmut.ssps_server.service.interf.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElse(null);
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
