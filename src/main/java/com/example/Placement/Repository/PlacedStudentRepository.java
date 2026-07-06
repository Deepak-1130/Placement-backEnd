package com.example.Placement.Repository;

import com.example.Placement.Models.PlacedStudentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacedStudentRepository extends JpaRepository<PlacedStudentData,Integer> {
}
