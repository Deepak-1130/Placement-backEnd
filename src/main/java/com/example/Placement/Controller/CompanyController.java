package com.example.Placement.Controller;

import com.example.Placement.Enums.CompanyType;
import com.example.Placement.Models.Company;
import com.example.Placement.Repository.CompanyRepository;
import com.example.Placement.Services.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.ErrorManager;

@RestController
@CrossOrigin("*")
public class CompanyController {

    @Autowired
     private CompanyService companyService;
     private final ObjectMapper objectMapper;

    public CompanyController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    //companies  add pandranala list ah potruka
    @PostMapping("/addCompany")
     public String addCompany(@RequestBody List<Company> company){
        return companyService.addCompany(company);
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerCompanyDetails(
            @RequestPart("company") String companyJson,
            @RequestPart("profilePic") MultipartFile profilePic)
            {

        try {

            Company company = objectMapper.readValue(companyJson, Company.class);

            Company registeredCompanyDetails = companyService.registerCompanyDetails(company, profilePic);

            return ResponseEntity.ok(registeredCompanyDetails);
        } catch (Exception e) {
             return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }

    //To get company based on their type
    @GetMapping("/getCompanyByType/{type}")
     public List<Company> getCompanyByType(@PathVariable CompanyType type){
        return companyService.getCompanyByType(type);
}
  //to get all company
    @GetMapping("/getCompany")
    public List<Company> getCompany(){

        return companyService.getCompany();
    }
  // to get companyBased on packages

    @GetMapping("/getCompanyByPackage/{paid}/{type}")
    public List<Company> getCompanyByPackages(@PathVariable int paid, @PathVariable CompanyType type){
     return companyService.getCompanyByPackages(paid,type);
    }
   // Update Company Details
    @PutMapping("/updateCompany")
  public String updateDetails( @RequestBody Company company){
        return  companyService.updateDetails(company);

    }
}
