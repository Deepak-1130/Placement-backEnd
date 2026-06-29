package com.example.Placement.Controller;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Services.StudentService;
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

    @PostMapping("/addStudent")
    public String addStudentDetail(@RequestBody Student student){
        return studentService.addStudentDetail( student);
    }
 @PostMapping("/addStudents")
 public String addStudents(@RequestBody List<Student> students){
        return studentService.addStudent(students);
 }

    @PostMapping("/addResume/{regNo}")
    public String addResume(@PathVariable long regNo , @RequestParam("resume") MultipartFile resume) {
        Student student = studentService.getStudentById(regNo);
        try{
            student.setResume(resume.getBytes());
            studentService.addStudentDetail(student);
        }
        catch (IOException e ){
            return "Error in file uploading";
        }
        return "done ";
    }

    @GetMapping("/getStudent/{regNo}")
    public Student getStudent(@PathVariable long regNo){
       return studentService.getStudentById(regNo);
    }

  @GetMapping("/getStudentsByDept/{dept}")
  public List<Student> getStudentsByDept(@PathVariable Departments dept){
       return studentService.getStudentsByDept(dept);
  }

    @GetMapping("/getResume/{regNo}")
    public ResponseEntity<byte[]> getResume(@PathVariable long regNo){
       Student student= studentService.getStudentById(regNo);
       return ResponseEntity.ok()
               .header("Content-Disposition","attachment;filename="  + student.getFirstName() + "resume.pdf" )
               .body(student.getResume());

        }
        @GetMapping("/getStudentByYear/{year}")
    public List<Student> getStudentByYear(@PathVariable int year){
        return studentService.getStudentByPassedOutYear(year);
        }

}
