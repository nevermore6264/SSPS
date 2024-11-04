package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.model.user.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Column(nullable = false)
    int staffPrintID;

    @Column(name = "printer_id", nullable = false)
    int printerToPrintID;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    Student student;

    @Column(nullable = false)
    LocalDateTime time;

    public void setStudent(Optional<Student> byUserEmail) {
        this.student = byUserEmail.orElse(null);
    }
}
