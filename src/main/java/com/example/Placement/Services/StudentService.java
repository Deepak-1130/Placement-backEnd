package com.example.Placement.Services;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class StudentService {
    private final String UploadDir ="uploads/";
    @Autowired
    private StudentRepository studentRepository;

    public String addStudentDetailAndProfile(Student student ,MultipartFile profilePic) throws IOException {
        Files.createDirectories(Paths.get(UploadDir+"StudentsProfile/"));
        String fileName = "-profilepic.jpg";
        String filePath=UploadDir+"StudentsProfile/"+student.getRegisterNumber()+fileName;
        Files.copy(profilePic.getInputStream(),Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING );
        System.out.println("profile pic stored successfully");
        studentRepository.save(student);
        System.out.println("Students data stored successfully ");

        return "Student added successfully";
    }


    public Student getStudentById(long regNo){
       return studentRepository.findById(regNo).orElse(null);
    }


    public List<Student> getStudentsByDept(Departments dept) {
        return studentRepository.findByDept(dept);
    }


    public String addStudent(List<Student> students) {
        studentRepository.saveAll(students);
      return "Added successfully";
    }


    public List<Student> getStudentByPassedOutYear(int year){
        return studentRepository.findByYear(year);
    }

    public void addResume(long registerNumber, MultipartFile resume) throws Exception {
        Files.createDirectories(Paths.get(UploadDir+"Resume/"));
        String fileName ="-resume.pdf";
        String filePath = UploadDir+"Resume/"+registerNumber+fileName;
        Files.copy(resume.getInputStream(),Paths.get(filePath),StandardCopyOption.REPLACE_EXISTING);
        System.out.print("Resume Stored Successfully");

    }
}
