package com.example.Placement.Controller;

import com.example.Placement.Enums.PlacedStatus;
import com.example.Placement.Models.PlacedStudentData;
import com.example.Placement.Services.PlacedStudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class PlacedStudentDataController {
    @Autowired
    private PlacedStudentService placedStudentService;
    private final  ObjectMapper objectMapper;

    public PlacedStudentDataController(PlacedStudentService placedStudentService, ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }
    
    @PostMapping("/markPlacedStudent")
    public ResponseEntity<?> markPlacedStudent(@RequestPart("placedStudentData") String  placedStudent
            , @RequestPart MultipartFile  offerLetter){

        try{
            PlacedStudentData placedStudentData1 = objectMapper.readValue(placedStudent,PlacedStudentData.class);
            PlacedStudentData placedStudentData2 = placedStudentService.markPlacedStudent(placedStudentData1, offerLetter);
            return ResponseEntity.ok(placedStudentData2);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getPlacedStudents")
    public List<PlacedStudentData> getPlacedStudents(){
        return placedStudentService.getPlacedStudents();

    }

}
