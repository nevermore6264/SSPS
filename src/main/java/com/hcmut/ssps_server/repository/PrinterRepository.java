package com.hcmut.ssps_server.repository;

import com.hcmut.ssps_server.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrinterRepository extends JpaRepository<Printer, Long> {
}
