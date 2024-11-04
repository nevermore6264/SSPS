package com.hcmut.ssps_server.model;

import com.hcmut.ssps_server.enums.PrinterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "printer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int printerID;

    @Column(nullable = false)
    String printerLocation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PrinterStatus status;

    @Column(nullable = false)
    @Min(0)
    int papersLeft;

    @Column(nullable = false)
    String adminPrintMail;

    // A list of available document types, stored as a single string líst
    @ElementCollection
    @CollectionTable(name = "available_doc_types", joinColumns = @JoinColumn(name = "printer_id"))
    @Column(name = "doc_type")
    List<String> availableDocType = new ArrayList<>();
}
