package com.example.Placement.Controller;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController

public class StudentController {

@Autowired
private StudentService studentService;
private final ObjectMapper objectMapper;

public StudentController(ObjectMapper objectMapper){
    this.objectMapper=objectMapper;
}

    @PostMapping("/addStudent")
    public String addStudentDetail(
            @RequestPart("student") String student,
            @RequestPart("profilePic") MultipartFile profilePic){

        try{

            Student students = objectMapper.readValue(student, Student.class);

            return studentService.addStudentDetailAndProfile( students , profilePic);
        }
        catch (Exception e){
            return (e.getMessage());
        }

    }

 @PostMapping("/addStudents")
 public String addStudents(@RequestBody List<Student> students){
        return studentService.addStudent(students);
 }

@PostMapping("/addResume/{regNo}")
public String addResume(@RequestPart MultipartFile resume, @PathVariable long regNo){
    try{
        Student student = studentService.getStudentById(regNo);
        studentService.addResume(student.getRegisterNumber(),resume);
    } catch (Exception e) {
        System.out.print("Server says " + e.getMessage());
    }

    return "Resume Stored Successfully";
}

    @GetMapping("/getStudent/{regNo}")
    public Student getStudent(@PathVariable long regNo){

        return studentService.getStudentById(regNo);
    }

  @GetMapping("/getStudentsByDept/{dept}")
  public List<Student> getStudentsByDept(@PathVariable Departments dept){
       return studentService.getStudentsByDept(dept);
  }


        @GetMapping("/getStudentByYear/{year}")
    public List<Student> getStudentByYear(@PathVariable int year){
        return studentService.getStudentByPassedOutYear(year);
        }

}
