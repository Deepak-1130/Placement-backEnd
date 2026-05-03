package com.example.Placement.Services;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public String addStudentDetail(@org.springframework.lang.NonNull Student student) {
        studentRepository.save(student);
        return "Student added successfully";
    }
    public Student getStudentById(long studentId){
       return studentRepository.findById(studentId).orElse(null);
    }

    public List<Student> getStudentsByDept(Departments dept) {
        return studentRepository.findByDept(dept);
    }

    public String addStudent(@NonNull List<Student> students) {
        studentRepository.saveAll(students);
      return "Added successfully";
    }

    public List<Student> getStudentByPassedOutYear(int year){
        return studentRepository.findByPassedOutYear(year);
    }
   public String updateStudent(long studentId, @NonNull Student student) {

    var existing = studentRepository.findById(studentId);

    if (existing.isEmpty()) {
        return "NOT_FOUND";
    }

    Student exist = existing.get();

    if (student.getFirstName() != null) {
        exist.setFirstName(student.getFirstName());
    }

    if (student.getLastName() != null) {
        exist.setLastName(student.getLastName());
    }

    if (student.getResumeUrl() != null) {
        exist.setResumeUrl(student.getResumeUrl());
    }

    if (student.getCgpa() !=0.0) {
        exist.setCgpa(student.getCgpa());
    }

    if (student.getFatherName() != null) {
        exist.setFatherName(student.getFatherName());
    }

    if (student.getMotherName() != null) {
        exist.setMotherName(student.getMotherName());
    }

    if (student.getPassedOutYear() != 0) {
        exist.setPassedOutYear(student.getPassedOutYear());
    }

    if (student.getDepartments() != null) {
        exist.setDepartments(student.getDepartments());
    }

    // ✅ FIXED EMAIL CHECK
    if (student.getEmailId() != null) {

        var exists = studentRepository.findByEmailId(student.getEmailId());

        if (exists.isPresent()) {
            return "EMAIL_EXISTS";
        }

        exist.setEmailId(student.getEmailId());
    }

    if (student.getHistoryOfArrears() != 0) {
        exist.setHistoryOfArrears(student.getHistoryOfArrears());
    }

    if (student.getNativePlace() != null) {
        exist.setNativePlace(student.getNativePlace());
    }

    studentRepository.save(exist);

    return "UPDATED";
}
    public List<Student> showall(){
        return studentRepository.findAll();
        
    }

    public String deleteByRegisterNo(long studentId){
          
            if(studentRepository.existsById(studentId)){
             studentRepository.deleteById(studentId);
             return "Deleted Successfully";
            }
            return "Not Found Register Number";

    }
}
