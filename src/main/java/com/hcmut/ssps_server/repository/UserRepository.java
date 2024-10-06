package com.hcmut.ssps_server.repository;

import com.hcmut.ssps_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
