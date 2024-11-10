package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.model.Document;

public interface IPrintingService {
    void addPrintRequest(Document document, int printerId);
}
