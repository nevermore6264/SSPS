package com.hcmut.ssps_server.service.implement;

import com.hcmut.ssps_server.dto.request.StudentCreationRequest;
import com.hcmut.ssps_server.dto.response.StudentResponse;
import com.hcmut.ssps_server.exception.AppException;
import com.hcmut.ssps_server.exception.ErrorCode;
import com.hcmut.ssps_server.mapper.UserMapper;
import com.hcmut.ssps_server.model.Document;
import com.hcmut.ssps_server.model.enums.PrintableStatus;
import com.hcmut.ssps_server.model.user.Student;
import com.hcmut.ssps_server.model.user.User;
import com.hcmut.ssps_server.repository.DocumentRepository;
import com.hcmut.ssps_server.repository.UserRepository.StudentRepository;
import com.hcmut.ssps_server.repository.UserRepository.UserRepository;
import com.hcmut.ssps_server.service.interf.IStudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentService implements IStudentService {
    UserRepository userRepository;
    UserMapper userMapper;
    StudentRepository studentRepository;

    @Override
    public Student createStudent(StudentCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
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

        Student student = studentRepository.findByUser_Username(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getStudentId());
        response.setNumOfPages(student.getNumOfPages());
        response.setUsername(student.getUser().getUsername());
        response.setFullName(student.getUser().getFullName());

        return response;
    }

    DocumentRepository documentRepo;

    PrinterService printerService;

    @Override
    public String uploadDocument(MultipartFile file, int printerId) throws IOException {
        String fileType = file.getContentType();
        PrintableStatus printable = printerService.isPrintable(printerId, file);

        if (printable == PrintableStatus.PRINTABLE) {
            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setFileType(fileType);
            document.setFileSize(file.getSize());
            document.setFileData(file.getBytes());
            document.setPageCount(printerService.caculatePage(fileType, file.getInputStream()));

            documentRepo.save(document);
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
