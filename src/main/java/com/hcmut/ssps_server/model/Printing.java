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
    private int id;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    private int staffPrintID;

    @OneToMany
    @JoinTable(name = "printing_printers",
            joinColumns = @JoinColumn(name = "printing_id"),
            inverseJoinColumns = @JoinColumn(name = "printer_id")
    )
    private List<Printer> listPrinters;

    // Expiration time only be allocated when the document was printed
    @Column(nullable = true)
    private LocalDateTime expiredTime;
}
