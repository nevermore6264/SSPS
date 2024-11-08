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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrintingService implements IPrintingService {
    PrintingRepository printingRepository;

    @Override
    public void addPrintRequest(Document document, int printerId) {
        Printing printing = new Printing();
        printing.setDocument(document);
        printing.setPrintingStartTime(LocalDateTime.now());
        printing.setPrinterToPrintID(printerId);
        var context = SecurityContextHolder.getContext();
        printing.setStudentUploadMail(context.getAuthentication().getName());
        printingRepository.save(printing);
    }

    //3 TRƯỜNG HỢP: STUDENT CONFIRM RECEIVE DOC hoặc MÁY IN BỊ LỖI NÊN HỦY YÊU CẦU ĐANG TỒN TẠI hoặc TÀI LIỆU HẾT THỜI GIAN TỒN TẠI
    public void deletePrintRequest(int printingId) {
        printingRepository.deleteById((long) printingId);
    }
}
