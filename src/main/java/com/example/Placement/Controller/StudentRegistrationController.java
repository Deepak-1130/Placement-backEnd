package com.example.Placement.Controller;

import com.example.Placement.Enums.AcademicYear;
import com.example.Placement.Enums.Department;
import com.example.Placement.Models.StudentRegistration;
import com.example.Placement.Services.StudentRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class StudentRegistrationController {

    @Autowired
    private StudentRegistrationService studentRegistrationService;

    // Create - Submit registration
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addRegistration(@RequestBody StudentRegistration registration) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = studentRegistrationService.addRegistration(registration);
            if (result.contains("successful")) {
                response.put("success", true);
                response.put("message", result);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Read - Get all registrations
    @GetMapping
    public ResponseEntity<List<StudentRegistration>> getAllRegistrations() {
        List<StudentRegistration> registrations = studentRegistrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    // Read - Get registration by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentRegistration> getRegistrationById(@PathVariable Long id) {
        StudentRegistration registration = studentRegistrationService.getRegistrationById(id);
        if (registration != null) {
            return ResponseEntity.ok(registration);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update - Full update
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRegistration(@PathVariable Long id, @RequestBody StudentRegistration registration) {
        Map<String, Object> response = new HashMap<>();
        try {
            StudentRegistration updated = studentRegistrationService.updateRegistration(id, registration);
            if (updated != null) {
                response.put("success", true);
                response.put("message", "Registration updated successfully");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Patch - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patchRegistration(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Map<String, Object> response = new HashMap<>();
        try {
            StudentRegistration updated = studentRegistrationService.patchRegistration(id, updates);
            if (updated != null) {
                response.put("success", true);
                response.put("message", "Registration updated successfully");
                response.put("data", updated);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Delete - Delete registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRegistration(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = studentRegistrationService.deleteRegistration(id);
            if (result.contains("deleted")) {
                response.put("success", true);
                response.put("message", result);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentRegistration>> getByDepartment(@PathVariable Department department) {
        List<StudentRegistration> students = studentRegistrationService.getByDepartment(department);
        return ResponseEntity.ok(students);
    }

    // Get by academic year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<StudentRegistration>> getByYear(@PathVariable AcademicYear year) {
        List<StudentRegistration> students = studentRegistrationService.getByYear(year);
        return ResponseEntity.ok(students);
    }

    // Get eligible students by CGPA
    @GetMapping("/eligible/{minCgpa}")
    public ResponseEntity<List<StudentRegistration>> getEligibleStudents(@PathVariable double minCgpa) {
        List<StudentRegistration> students = studentRegistrationService.getEligibleStudents(minCgpa);
        return ResponseEntity.ok(students);
    }

    // Get students with no arrears
    @GetMapping("/no-arrears")
    public ResponseEntity<List<StudentRegistration>> getStudentsWithNoArrears() {
        List<StudentRegistration> students = studentRegistrationService.getStudentsWithNoArrears();
        return ResponseEntity.ok(students);
    }

    // Upload profile picture
    @PostMapping(value = "/{id}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadProfilePicture(@PathVariable Long id,
                                                                     @RequestPart("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            String url = studentRegistrationService.uploadProfilePicture(id, file);
            if (url.contains("not found")) {
                response.put("success", false);
                response.put("message", url);
                return ResponseEntity.notFound().build();
            }
            response.put("success", true);
            response.put("message", "Profile picture uploaded successfully");
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Upload resume
    @PostMapping(value = "/{id}/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadResume(@PathVariable Long id,
                                                            @RequestPart("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            String url = studentRegistrationService.uploadResume(id, file);
            if (url.contains("not found")) {
                response.put("success", false);
                response.put("message", url);
                return ResponseEntity.notFound().build();
            }
            response.put("success", true);
            response.put("message", "Resume uploaded successfully");
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}