package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.model.user.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "printingLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrintingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false, referencedColumnName = "id")
    Document document;

    int staffPrintID;

    @ManyToOne
    @JoinColumn(name = "printerid", nullable = false)
    Printer printer;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @Column(nullable = false)
    LocalDateTime time;
}
