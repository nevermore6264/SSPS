package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.dto.request.StudentCreationRequest;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.exception.AppException;
import com.hcmut.ssps_server.exception.ErrorCode;
import com.hcmut.ssps_server.mapper.StudentMapper;
import com.hcmut.ssps_server.mapper.UserMapper;
import com.hcmut.ssps_server.model.user.Student;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.repository.UserRepository.StudentRepository;
import com.hcmut.ssps_server.repository.UserRepository.UserRepository;
import com.hcmut.ssps_server.service.interf.IStudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentService implements IStudentService {
    UserRepository userRepository;
    UserMapper userMapper;
    StudentMapper studentMapper;
    StudentRepository studentRepository;

    @Override
    public Student createStudent(StudentCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setRole("STUDENT");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Student student = new Student();
        student.setUser(user);
        student.setNumOfPages(0);
        student.setStudentId(Long.parseLong(request.getStudentId()));

        userRepository.save(user);
        return studentRepository.save(student);
    }

    @Override
    public StudentResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info("authority: " + context.getAuthentication().getAuthorities());
        Student student = studentRepository.findByUser_Username(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return studentMapper.toStudentResponse(student);
    }
}
