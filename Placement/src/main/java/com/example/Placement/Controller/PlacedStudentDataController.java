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
    public String markPlacedStudent(@RequestBody PlacedStudentData  placedStudent){
        return placedStudentService.markPlacedStudent(placedStudent);
    }

    @PostMapping("/uploadOfferLetter/{placedId}")
    public String uploadOfferLetter(@PathVariable int placedId , @RequestPart MultipartFile offerLetter) throws IOException {
        try{
            PlacedStudentData placedStudentData = placedStudentService.getPlacedStudent(placedId);
            placedStudentService.UploadOfferLetter(placedStudentData,offerLetter);
            return "file uploaded successfully ";

        }catch (IOException e ){
            return "Error in Uploading";
        }

    }
    @GetMapping("/getPlacedData/{rollNo}")
        public List<PlacedStudentData> getPlacedStudentByRollNo(@PathVariable long rollNo){
            return placedStudentService.getPlacedStudentByRollNo(rollNo);

    }
    @GetMapping("/getPlacedStudents")
    public List<PlacedStudentData> getPlacedStudents(){
        return placedStudentService.getPlacedStudents();

    }

}
