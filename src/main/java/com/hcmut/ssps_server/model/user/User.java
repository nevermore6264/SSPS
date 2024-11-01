package com.hcmut.ssps_server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(name = "full_name" ,nullable = true, length = 50)
    String fullName;

    @Column(name = "role",nullable = false)
    String role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    Student student;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    Admin admin;
}
