package com.hcmut.ssps_server.repository.UserRepository;

import com.hcmut.ssps_server.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser_Username(String username);
}
