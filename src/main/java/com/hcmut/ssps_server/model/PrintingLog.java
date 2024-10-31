package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.model.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "printingLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrintingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false, referencedColumnName = "id")
    private Document document;

    private int staffPrintID;

    @ManyToOne
    @JoinColumn(name = "printerid", nullable = false)
    private Printer printer;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDateTime time;
}
