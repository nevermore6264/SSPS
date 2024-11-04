package com.hcmut.ssps_server.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "printing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Printing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false)
    Document document;

    @Column(nullable = false, unique = true)
    String studentUploadMail;

    @Column(nullable = false)
    int staffPrintID;

    @Column(nullable = false)
    int printerToPrintID;

    // Expiration time only be allocated when the document was printed
    @Column(nullable = true)
    LocalDateTime expiredTime;
}
