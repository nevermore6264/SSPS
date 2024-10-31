package com.hcmut.ssps_server.service.implement;


import com.hcmut.ssps_server.model.Printer;
import com.hcmut.ssps_server.model.enums.PrintableStatus;
import com.hcmut.ssps_server.repository.PrinterRepository;
import com.hcmut.ssps_server.service.interf.IPrinterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrinterService implements IPrinterService {
    PrinterRepository printerRepo;

    @Override
    public PrintableStatus isPrintable(int printerId, MultipartFile file) {
        try {
            Optional<Printer> printer = printerRepo.findById((long) printerId);

            List<String> availableDocType = printer.get().getAvailableDocType();
            //CHECK FILE AVAILABLE
            String fileType = file.getContentType();
            if(availableDocType.contains(fileType)){
                //CHECK PAPER
                if (printer.get().getPapersLeft() < caculatePage(fileType, file.getInputStream())) {
                    return PrintableStatus.PRINTABLE;
                } else {
                    return PrintableStatus.PRINTER_NOT_HAVE_ENOUGH_PAPERS;
                }
            }
            else
                return PrintableStatus.UNSUPPORTED_FILE_TYPE;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int caculatePage(String fileType, InputStream inputStream) throws IOException {
        switch (fileType) {
            case "application/pdf":
                return calculatePageCountForPdf(inputStream);

            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return calculatePageCountForWord(inputStream);

            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return calculatePageCountForExcel(inputStream);

            case "image/tiff":
                return calculatePageCountForTiff(inputStream);

            case "image/jpeg":
            case "image/png":
            case "image/gif":
                return 1;
            default:
                throw new UnsupportedOperationException("Unsupported file type: " + fileType);
        }
    }

    public int calculatePageCountForTiff(InputStream inputStream) throws IOException {
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("TIFF");

            if (!readers.hasNext()) {
                throw new IOException("No ImageReader found for TIFF format");
            }

            ImageReader reader = readers.next();
            reader.setInput(imageInputStream, true);

            int pageCount = reader.getNumImages(true);

            reader.dispose();

            return pageCount;
        }
    }

    public int calculatePageCountForExcel(InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            return workbook.getNumberOfSheets(); // Trả về số trang tính (sheets) trong file Excel
        }
    }

    public int calculatePageCountForWord(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            return document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        }
    }

    public int calculatePageCountForPdf(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            return document.getNumberOfPages();
        }
    }
}
