package com.hcmut.ssps_server.repository.UserRepository;

import com.hcmut.ssps_server.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser_Email(String email);

    @Query("SELECT s FROM Student s")
    Page<Student> findAllStudents(Pageable pageable);
}
