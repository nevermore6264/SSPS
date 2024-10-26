package com.hcmut.ssps_server.model;
import java.util.List;
import java.util.Queue;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long printerID;

    @Column(nullable = false)
    String printerLocation;

    @Column(nullable = false)
    boolean printerStatus;

    @Column(nullable = false)
    @Min(0)
    int papersLeft;

    @ElementCollection
    @CollectionTable(name = "available_doc_types", joinColumns = @JoinColumn(name = "printer_id"))
    @Column(name = "doc_type")
    List<String> availableDocType;

    @ElementCollection
    @CollectionTable(name = "student_queue", joinColumns = @JoinColumn(name = "printer_id"))
    @Column(name = "student_id")
    Queue<Integer> studentIDQueue;
}