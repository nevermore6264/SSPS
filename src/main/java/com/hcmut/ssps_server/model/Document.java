package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.model.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int studentUpload;

    private String fileName;

    private String fileType;

    private double fileSize;

    private int pageCount;
}
