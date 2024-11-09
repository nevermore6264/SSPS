package com.hcmut.ssps_server.model.user;

import com.hcmut.ssps_server.model.Document;
import com.hcmut.ssps_server.model.PrintingLog;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    @Id
    @Column(nullable = false, unique = true)
    Long studentId;

    @Column(name = "num_of_pages")
    Integer numOfPages;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    //History of this student printing
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<PrintingLog> logList;
}
///