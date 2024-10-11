package com.hcmut.ssps_server.model.user;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 20, unique = true)
    String username;

    @Column(nullable = false, length = 20)
    String password;

    @Column(name = "full_name" ,nullable = true, length = 50)
    String fullName;
}
