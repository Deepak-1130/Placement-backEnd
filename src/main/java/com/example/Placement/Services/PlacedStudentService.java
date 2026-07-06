package com.example.Placement.Services;

import com.example.Placement.Enums.PlacedStatus;
import com.example.Placement.Models.PlacedStudentData;
import com.example.Placement.Repository.PlacedStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PlacedStudentService {
    @Autowired
    private PlacedStudentRepository placedStudentRepository;
    public final String uploadDir = "uploads/offers/";

    public PlacedStudentData markPlacedStudent(PlacedStudentData placedStudentData, MultipartFile offerLetter) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = placedStudentData.getStudent().getRegisterNumber()+"-offer.pdf";
        Path filePath =  Paths.get(uploadDir+fileName);
        Files.copy(offerLetter.getInputStream(),filePath);
        placedStudentData.setOfferPath(fileName);
        placedStudentRepository.save(placedStudentData);
        return placedStudentData;

    }

    public List<PlacedStudentData> getPlacedStudents() {
        return placedStudentRepository.findAll();
    }
}
