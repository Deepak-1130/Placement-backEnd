package com.example.Placement.Controller;

import com.example.Placement.Enums.CompanyType;
import com.example.Placement.Models.Company;
import com.example.Placement.Repository.CompanyRepository;
import com.example.Placement.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
//companies  add pandranala list ah potruka
    @PostMapping("/addCompany")
    public String addCompany(@RequestBody List<Company> company){
        return companyService.addCompany(company);
    }
    //To get company based on their type
@GetMapping("/getCompanyByType/{type}")
    public List<Company> getCompanyByType(@PathVariable CompanyType type){
        return companyService.getCompanyByType(type);
}



}
