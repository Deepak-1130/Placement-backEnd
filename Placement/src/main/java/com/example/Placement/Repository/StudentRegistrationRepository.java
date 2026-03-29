package com.example.Placement.Repository;

import com.example.Placement.Enums.AcademicYear;
import com.example.Placement.Enums.Department;
import com.example.Placement.Models.StudentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, Long> {

    // Check duplicates before saving
    boolean existsByRegisterNumber(String registerNumber);
    boolean existsByEmail(String email);

    // Get all students by department
    @Query("SELECT s FROM StudentRegistration s WHERE s.department = :department")
    List<StudentRegistration> getByDepartment(@Param("department") Department department);

    // Get all students by year
    @Query("SELECT s FROM StudentRegistration s WHERE s.year = :year")
    List<StudentRegistration> getByYear(@Param("year") AcademicYear year);

    // Get students eligible by CGPA cutoff
    @Query("SELECT s FROM StudentRegistration s WHERE s.cgpa >= :minCgpa ORDER BY s.cgpa DESC")
    List<StudentRegistration> getEligibleStudents(@Param("minCgpa") double minCgpa);

    // Get students with no arrears
    @Query("SELECT s FROM StudentRegistration s WHERE s.historyOfArrears = 0")
    List<StudentRegistration> getStudentsWithNoArrears();
    
    // Search students by name or register number
    @Query("SELECT s FROM StudentRegistration s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.registerNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<StudentRegistration> searchStudents(@Param("query") String query);
}