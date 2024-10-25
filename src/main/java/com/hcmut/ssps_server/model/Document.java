package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.model.user.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long docId;

    @Column(nullable = false, unique = true)
    Long studentUploadID;

    @Column(nullable = false)
    String fileName;

    @Column(nullable = false)
    String fileType;

    @Column(nullable = false)
    double fileSize;

    @Column(nullable = false)
    int pageCount;

    @ManyToOne
    @JoinColumn(name = "studentUploadID", referencedColumnName = "studentId", insertable = false, updatable = false)
    Student student;
}