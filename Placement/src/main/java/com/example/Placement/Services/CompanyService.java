package com.example.Placement.Services;

import com.example.Placement.Enums.CompanyType;
import com.example.Placement.Models.Company;
import com.example.Placement.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    private final String uploadDir = "uploads/";

    public String addCompany(List<Company> company) {
      companyRepository.saveAll(company);
      return "company added successfully";
    }

    public List<Company> getCompanyByType(CompanyType type) {
        return companyRepository.getAllCompanyByType(type);
    }

    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    public List<Company> getCompanyByPackages(int paid, CompanyType type) {
       return  companyRepository.getCompanyByPackages(paid,type);
    }
    public Company registerCompanyDetails(Company company, MultipartFile profilePic) throws IOException {

        Files.createDirectories(Paths.get(uploadDir));
        String profilePicName = company.getEmail() + "_profile_" + StringUtils.cleanPath(profilePic.getOriginalFilename());
        Path profilePath = Paths.get(uploadDir + profilePicName);
        Files.copy(profilePic.getInputStream(), profilePath, StandardCopyOption.REPLACE_EXISTING);
        company.setLogoPicPath(profilePicName);



        return companyRepository.save(company);
    }
}
