package com.example.Placement.Repository;

import com.example.Placement.Models.PlacedStudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacedStudentRepository extends JpaRepository<PlacedStudentData,Integer> {

    @Query("SELECT d FROM PlacedStudentData d WHERE d.student= :rollNo")
    List<PlacedStudentData> findByRollNo(@Param("rollNo") long rollNo);

}
