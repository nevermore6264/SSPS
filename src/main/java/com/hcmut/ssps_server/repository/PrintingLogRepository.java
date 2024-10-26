package com.hcmut.ssps_server.repository;

import com.hcmut.ssps_server.model.PrintingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintingLogRepository extends JpaRepository<PrintingLog, Long> {
}
