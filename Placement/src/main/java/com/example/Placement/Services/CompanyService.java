package com.example.Placement.Services;

import com.example.Placement.Enums.CompanyType;
import com.example.Placement.Models.Company;
import com.example.Placement.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public String addCompany(List<Company> company) {
      companyRepository.saveAll(company);
      return " company added successfully";
    }

    public List<Company> getCompanyByType(CompanyType type) {
        return companyRepository.getAllCompanyByType(type);
    }
}
