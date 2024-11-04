package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.dto.request.StudentCreationRequest;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.exception.AppException;
import com.hcmut.ssps_server.exception.ErrorCode;
import com.hcmut.ssps_server.mapper.StudentMapper;
import com.hcmut.ssps_server.mapper.UserMapper;
import com.hcmut.ssps_server.model.Document;
import com.hcmut.ssps_server.enums.PrintableStatus;
import com.hcmut.ssps_server.model.Printer;
import com.hcmut.ssps_server.model.user.Student;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.repository.DocumentRepository;
import com.hcmut.ssps_server.repository.PrinterRepository;
import com.hcmut.ssps_server.repository.UserRepository.StudentRepository;
import com.hcmut.ssps_server.repository.UserRepository.UserRepository;
import com.hcmut.ssps_server.service.interf.IStudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentService implements IStudentService {
    UserRepository userRepository;
    UserMapper userMapper;
    StudentMapper studentMapper;
    StudentRepository studentRepository;
    DocumentRepository documentRepo;
    PrinterService printerService;
    PrintingService printingService;
    PrinterRepository printerRepository;

    @Override
    public Student createStudent(StudentCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setRole("STUDENT");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Student student = new Student();
        student.setUser(user);
        student.setNumOfPages(0);
        student.setStudentId(Long.parseLong(request.getStudentId()));

        userRepository.save(user);
        return studentRepository.save(student);
    }

    @Override
    public StudentResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info("authority: " + context.getAuthentication().getAuthorities());
        Student student = studentRepository.findByUser_Email(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return studentMapper.toStudentResponse(student);
    }

    //CHƯA CÓ CÁC HÀM NHƯ CONFIRM RECEIVE DOC

    @Override
    public String uploadDocument(MultipartFile file, int printerId) throws IOException {
        String fileType = file.getContentType();
        Optional<Printer> printer = printerRepository.findById((long) printerId);
        PrintableStatus printable = printerService.isPrintable(printer.orElse(null), file);

        if (printable == PrintableStatus.PRINTABLE) {
            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setFileType(fileType);
            document.setFileSize(file.getSize());
            document.setFileData(file.getBytes());
            document.setPageCount(printerService.caculatePage(fileType, file.getInputStream()));

            //Save document to database
            documentRepo.save(document);

            //Store ALL print request to Printing
            printingService.addPrintRequest(document, printerId);

            return "Upload success";

        } else if (printable == PrintableStatus.UNSUPPORTED_FILE_TYPE) {
            return "Printer is not supported";

        } else if (printable == PrintableStatus.PRINTER_NOT_HAVE_ENOUGH_PAPERS) {
            return "Printer doesn't have enough papers";

        } else {
            //Nearly not exist this case
            return "Printer not found";
        }
    }


}
