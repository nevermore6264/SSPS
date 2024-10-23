package com.hcmut.ssps_server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "printer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int printerID;

    private String printerLocation;

    private boolean printerStatus;

    private int papersLeft;

    // A list of available document types, stored as a single string líst
    @ElementCollection
    @CollectionTable(name = "available_doc_types", joinColumns = @JoinColumn(name = "printer_id"))
    @Column(name = "doc_type")
    private List<String> availableDocType;

    // A queue to track student IDs waiting in line, stored as a list or separate table
    @ElementCollection
    @CollectionTable(name = "student_queue", joinColumns = @JoinColumn(name = "printer_id"))
    @Column(name = "student_id")
    private LinkedList<Integer> studentIDQueue;
}
