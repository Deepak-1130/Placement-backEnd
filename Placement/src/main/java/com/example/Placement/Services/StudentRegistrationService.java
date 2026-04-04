package com.example.Placement.Services;

import com.example.Placement.Enums.AcademicYear;
import com.example.Placement.Enums.Department;
import com.example.Placement.Models.StudentRegistration;
import com.example.Placement.Repository.StudentRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StudentRegistrationService {

    @Autowired
    private StudentRegistrationRepository studentRegistrationRepository;

    // ── Register a new student ─────────────────────────────────────────
    public String addRegistration(StudentRegistration registration) {
        if (studentRegistrationRepository.existsByRegisterNumber(registration.getRegisterNumber())) {
            return "Register number already exists!";
        }
        if (studentRegistrationRepository.existsByEmail(registration.getEmail())) {
            return "Email already registered!";
        }
        studentRegistrationRepository.save(registration);
        return "Registration successful";
    }

    // ── Get all registrations ──────────────────────────────────────────
    public List<StudentRegistration> getAllRegistrations() {
        return studentRegistrationRepository.findAll();
    }

    // ── Get registration by ID ─────────────────────────────────────────
    public StudentRegistration getRegistrationById(Long id) {
        return studentRegistrationRepository.findById(id).orElse(null);
    }

    // ── Update full registration ───────────────────────────────────────
    public StudentRegistration updateRegistration(Long id, StudentRegistration updatedRegistration) {
        StudentRegistration existing = studentRegistrationRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        // Check for duplicate register number if changed
        if (!existing.getRegisterNumber().equals(updatedRegistration.getRegisterNumber()) &&
            studentRegistrationRepository.existsByRegisterNumber(updatedRegistration.getRegisterNumber())) {
            throw new RuntimeException("Register number already exists!");
        }
        
        // Check for duplicate email if changed
        if (!existing.getEmail().equals(updatedRegistration.getEmail()) &&
            studentRegistrationRepository.existsByEmail(updatedRegistration.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        
        // Update all fields
        existing.setRegisterNumber(updatedRegistration.getRegisterNumber());
        existing.setFirstName(updatedRegistration.getFirstName());
        existing.setLastName(updatedRegistration.getLastName());
        existing.setPhone(updatedRegistration.getPhone());
        existing.setEmail(updatedRegistration.getEmail());
        existing.setDepartment(updatedRegistration.getDepartment());
        existing.setYear(updatedRegistration.getYear());
        existing.setGender(updatedRegistration.getGender());
        existing.setDob(updatedRegistration.getDob());
        existing.setNativePlace(updatedRegistration.getNativePlace());
        existing.setCgpa(updatedRegistration.getCgpa());
        existing.setHistoryOfArrears(updatedRegistration.getHistoryOfArrears());
        existing.setFatherName(updatedRegistration.getFatherName());
        existing.setMotherName(updatedRegistration.getMotherName());
        existing.setFatherOccupation(updatedRegistration.getFatherOccupation());
        existing.setMotherOccupation(updatedRegistration.getMotherOccupation());
        existing.setFamilyIncome(updatedRegistration.getFamilyIncome());
        existing.setParentPhone(updatedRegistration.getParentPhone());
        
        // Preserve file URLs if not updated
        if (updatedRegistration.getProfilePictureUrl() != null) {
            existing.setProfilePictureUrl(updatedRegistration.getProfilePictureUrl());
        }
        if (updatedRegistration.getResumeUrl() != null) {
            existing.setResumeUrl(updatedRegistration.getResumeUrl());
        }
        
        return studentRegistrationRepository.save(existing);
    }

    // ── Partial update registration ────────────────────────────────────
    public StudentRegistration patchRegistration(Long id, Map<String, Object> updates) {
        StudentRegistration existing = studentRegistrationRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        updates.forEach((key, value) -> {
            switch (key) {
                case "registerNumber":
                    if (!existing.getRegisterNumber().equals(value) &&
                        studentRegistrationRepository.existsByRegisterNumber((String) value)) {
                        throw new RuntimeException("Register number already exists!");
                    }
                    existing.setRegisterNumber((String) value);
                    break;
                case "firstName":
                    existing.setFirstName((String) value);
                    break;
                case "lastName":
                    existing.setLastName((String) value);
                    break;
                case "phone":
                    existing.setPhone((String) value);
                    break;
                case "email":
                    if (!existing.getEmail().equals(value) &&
                        studentRegistrationRepository.existsByEmail((String) value)) {
                        throw new RuntimeException("Email already registered!");
                    }
                    existing.setEmail((String) value);
                    break;
                case "department":
                    existing.setDepartment(Department.valueOf((String) value));
                    break;
                case "year":
                    existing.setYear(AcademicYear.valueOf((String) value));
                    break;
                case "gender":
                    existing.setGender(com.example.Placement.Enums.Gender.valueOf((String) value));
                    break;
                case "dob":
                    existing.setDob(LocalDate.parse((String) value));
                    break;
                case "nativePlace":
                    existing.setNativePlace((String) value);
                    break;
                case "cgpa":
                    existing.setCgpa(((Number) value).doubleValue());
                    break;
                case "historyOfArrears":
                    existing.setHistoryOfArrears(((Number) value).intValue());
                    break;
                case "fatherName":
                    existing.setFatherName((String) value);
                    break;
                case "motherName":
                    existing.setMotherName((String) value);
                    break;
                case "fatherOccupation":
                    existing.setFatherOccupation((String) value);
                    break;
                case "motherOccupation":
                    existing.setMotherOccupation((String) value);
                    break;
                case "familyIncome":
                    existing.setFamilyIncome(((Number) value).longValue());
                    break;
                case "parentPhone":
                    existing.setParentPhone((String) value);
                    break;
            }
        });
        
        return studentRegistrationRepository.save(existing);
    }

    // ── Get by department ──────────────────────────────────────────────
    public List<StudentRegistration> getByDepartment(Department department) {
        return studentRegistrationRepository.getByDepartment(department);
    }

    // ── Get by year ────────────────────────────────────────────────────
    public List<StudentRegistration> getByYear(AcademicYear year) {
        return studentRegistrationRepository.getByYear(year);
    }

    // ── Get eligible students by CGPA ──────────────────────────────────
    public List<StudentRegistration> getEligibleStudents(double minCgpa) {
        return studentRegistrationRepository.getEligibleStudents(minCgpa);
    }

    // ── Get students with no arrears ───────────────────────────────────
    public List<StudentRegistration> getStudentsWithNoArrears() {
        return studentRegistrationRepository.getStudentsWithNoArrears();
    }

    // ── Upload profile picture ─────────────────────────────────────────
    public String uploadProfilePicture(Long id, MultipartFile file) {
        StudentRegistration student = studentRegistrationRepository.findById(id).orElse(null);
        if (student == null) return "Student not found";
        String url = storeFile(file, "profiles");
        student.setProfilePictureUrl(url);
        studentRegistrationRepository.save(student);
        return url;
    }

    // ── Upload resume ──────────────────────────────────────────────────
    public String uploadResume(Long id, MultipartFile file) {
        StudentRegistration student = studentRegistrationRepository.findById(id).orElse(null);
        if (student == null) return "Student not found";
        String url = storeFile(file, "resumes");
        student.setResumeUrl(url);
        studentRegistrationRepository.save(student);
        return url;
    }

    // ── Delete registration ────────────────────────────────────────────
    public String deleteRegistration(Long id) {
        if (!studentRegistrationRepository.existsById(id)) {
            return "Registration not found";
        }
        studentRegistrationRepository.deleteById(id);
        return "Registration deleted successfully";
    }

    // ── Private file storage helper ────────────────────────────────────
    private String storeFile(MultipartFile file, String subDir) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetDir = Paths.get("uploads", subDir);
            Files.createDirectories(targetDir);
            Files.copy(file.getInputStream(), targetDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + subDir + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}