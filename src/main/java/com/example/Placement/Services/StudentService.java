package com.example.Placement.Services;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import com.example.Placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public String addStudentDetail(Student student) {
        studentRepository.save(student);
        return "Student added successfully";
    }
    public Student getStudentById(long studentId){
       return studentRepository.findById(studentId).orElse(null);
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
}
