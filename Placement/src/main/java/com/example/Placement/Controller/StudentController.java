package com.example.Placement.Controller;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController

public class StudentController {
@Autowired
private StudentService studentService;

    @PostMapping("/addStudent")
    public String addStudentDetail(@RequestBody @NonNull Student student){
        return studentService.addStudentDetail( student);
    }
 @PostMapping("/addStudents")
 public String addStudents(@RequestBody @NonNull List<Student> students){
        return studentService.addStudent(students);
 }

    // @PostMapping("/addResume/{regNo}")
    // public String addResume(@PathVariable long regNo , @RequestParam("resume") MultipartFile resume) {
    //     Student student = studentService.getStudentById(regNo);
    //     try{
    //         student.setResumeUrl(resume.getBytes());
    //         studentService.addStudentDetail(student);
    //     }
    //     catch (IOException e ){
    //         return "Error in file uploading";
    //     }
    //     return "done ";
    // }
@PostMapping("/addResume/{regNo}")
public String addResume(@PathVariable long regNo,
                        @RequestParam("resumeUrl") String resumeUrl) {

    Student student = studentService.getStudentById(regNo);

    student.setResumeUrl(resumeUrl);
    studentService.addStudentDetail(student);

    return "Resume URL saved successfully";
}
    @GetMapping("/getStudent/{regNo}")
    public Student getStudent(@PathVariable long regNo){
       return studentService.getStudentById(regNo);
    }

  @GetMapping("/getStudentsByDept/{dept}")
  public List<Student> getStudentsByDept(@PathVariable Departments dept){
       return studentService.getStudentsByDept(dept);
  }

    // @GetMapping("/getResume/{regNo}")
    // public ResponseEntity<String> getResume(@PathVariable long regNo){
    //    Student student= studentService.getStudentById(regNo);
    //    return ResponseEntity.ok()
    //            .header("Content-Disposition","attachment;filename="  + student.getFirstName() + "resume.pdf" )
    //            .body(student.getResume());

    //     }

        @GetMapping("/getResume/{regNo}")
public ResponseEntity<Void> getResume(@PathVariable long regNo) {
    Student student = studentService.getStudentById(regNo);
       if (student == null || student.getResumeUrl() == null) {
                return ResponseEntity.notFound().build();
       }
    return ResponseEntity.status(302)
            .header("Location", student.getResumeUrl())
            .build();
}
        @GetMapping("/getStudentByYear/{year}")
    public List<Student> getStudentByYear(@PathVariable int year){
        return studentService.getStudentByPassedOutYear(year);
        }

}
