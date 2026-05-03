package com.example.Placement.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Repository.StudentRepository;
import com.example.Placement.Services.StudentService;
import com.example.Placement.StudentDto.LoginRequest;
import com.example.Placement.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;



@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
     
    @Value("${file.upload-dir}")
    private String uploadDir;


     @Autowired
     private StudentRepository studentRepository;
   
     @PostMapping("/login")
     public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        String emailId=loginRequest.getEmailId();
        String password=loginRequest.getPassword();
        var userOptional=studentRepository.findByEmailId(emailId);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("USER NOT REGISTERED",HttpStatus.UNAUTHORIZED);
        }
        Student student=userOptional.get();
        if(!passwordEncoder.matches(password,student.getPassword())){
            return new ResponseEntity<>("INVALID USER",HttpStatus.UNAUTHORIZED);
        }
       String token=jwtUtil.generateToken(emailId);
       return ResponseEntity.ok(Map.of("token",token));

     } 


      // ✅ UPLOAD PROFILE PIC
    @PostMapping("/students/{registerNumber}/profile")
    public ResponseEntity<String> uploadProfile(
            @PathVariable Long registerNumber,
            @RequestParam("file") MultipartFile file) {

        try {
            Student student = studentService.getStudentById(registerNumber);
            if (student == null) return ResponseEntity.notFound().build();

            // create folder if not exists
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            // validation
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image allowed");
            }

            String fileName = registerNumber + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.write(path, file.getBytes());

            student.setProfilePicPath(path.toString());
            studentRepository.save(student);

            return ResponseEntity.ok("Profile uploaded");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed");
        }
    }

  
    @GetMapping("/students/{RegisterNumber}/profile")
    public ResponseEntity<Resource> getProfile(@PathVariable Long registerNumber) {

        try {
            Student student = studentService.getStudentById(registerNumber);
            if (student == null || student.getProfilePicPath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path path = Paths.get(student.getProfilePicPath());
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/getAll")
public ResponseEntity<List<Student>> getAll() {

    List<Student> students = studentService.showall();

    if (students.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }

    return new ResponseEntity<>(students, HttpStatus.OK); // 200
}
    
    @GetMapping("/getStudent/{regNo}")
    public Student getStudent(@PathVariable long regNo) {
        return studentService.getStudentById(regNo);
    }

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
    public List<Student> getStudentByYear(@PathVariable int year) {
        return studentService.getStudentByPassedOutYear(year);
    }

    @GetMapping("/getStudentsByDept/{dept}")
    public List<Student> getStudentsByDept(@PathVariable Departments dept) {
        return studentService.getStudentsByDept(dept);
    }

    // @PostMapping("/addStudent")
    // public String addStudentDetail(@RequestBody @NonNull Student student) {
    //     return studentService.addStudentDetail(student);
    // }

    @PostMapping("/addStudent")
public ResponseEntity<?> addStudentDetail(@RequestBody @NonNull Student student) {

    // ✅ Encode password before saving
    student.setPassword(passwordEncoder.encode(student.getPassword()));

    studentService.addStudentDetail(student);

    return ResponseEntity.ok("Student Registered Successfully");
}

    @PostMapping("/addResume/{regNo}")
    public String addResume(@PathVariable long regNo,
            @RequestParam("resumeUrl") String resumeUrl) {

        Student student = studentService.getStudentById(regNo);

        student.setResumeUrl(resumeUrl);
        studentService.addStudentDetail(student);

        return "Resume URL saved successfully";
    
            }
    @PutMapping("/update/{registerNumber}")
   public ResponseEntity<String> updateStudent(@PathVariable Long registerNumber,
                                            @RequestBody Student student) {

    var result = studentService.updateStudent(registerNumber, student);

    if (result.equals("UPDATED")) {
        return new ResponseEntity<>("Update Successful", HttpStatus.OK);
    }

    if (result.equals("EMAIL_EXISTS")) {
        return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
}
     @DeleteMapping("/delete/{registerNumber}")
     public ResponseEntity<String> getDelete(@PathVariable Long registerNumber){
                var result=studentService.deleteByRegisterNo(registerNumber);
                if(result.equals("Deleted Successfully")){
                    return new ResponseEntity<>(result,HttpStatus.OK);
                }
                return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
     }

}
