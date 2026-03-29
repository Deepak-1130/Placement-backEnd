package com.example.Placement.Repository;

import com.example.Placement.Enums.CompanyType;
import com.example.Placement.Models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company , Long> {

    @Query("SELECT c FROM Company c where c.companyType=:type")
    List<Company> getAllCompanyByType(@Param("type") CompanyType type);

    @Query("SELECT c FROM Company c where c.lastHighestPackage>=:paid AND c.companyType=:type")
    List<Company> getCompanyByPackages(@Param("paid") int paid,@Param("type") CompanyType type);
}
