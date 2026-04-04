package com.example.Placement.Repository;

import com.example.Placement.Enums.Departments;
import com.example.Placement.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student , Long> {

    @Query("SELECT s FROM Student s WHERE s.departments = :dept")
    List<Student> findByDept(@Param("dept")Departments dept);

    @Query("SELECT s FROM Student s WHERE s.passedOutYear= :year")
    List<Student> findByYear(@Param("year") int year);
}
