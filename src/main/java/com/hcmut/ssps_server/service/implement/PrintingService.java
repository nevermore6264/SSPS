package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.model.Document;
import com.hcmut.ssps_server.model.Printing;
import com.hcmut.ssps_server.repository.PrintingRepository;
import com.hcmut.ssps_server.service.interf.IPrintingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrintingService implements IPrintingService {
    PrintingRepository printingRepository;

    @Override
    public void createPrintRequest(Document document) {
        Printing printing = new Printing();
        printing.setDocument(document);
        var context = SecurityContextHolder.getContext();
        printing.setStudentUploadMail(context.getAuthentication().getName());
        printingRepository.save(printing);
    }
}