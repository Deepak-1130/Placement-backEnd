package com.example.Placement.Models;


import com.example.Placement.Enums.CompanyType;
import jakarta.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyId;
    private String companyName;
    private String branch;
    private String email;
    private String mobileNo;
    private Boolean histOfArrear;
    private int lastHighestPackage;
    private String logoPicPath;
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Boolean getHistOfArrear() {
        return histOfArrear;
    }

    public void setHistOfArrear(Boolean histOfArrear) {
       this.histOfArrear = histOfArrear;
    }

    public int getLastHighestPackage() {
        return lastHighestPackage;
    }

    public void setLastHighestPackage(int lastHighestPackage) {
        this.lastHighestPackage = lastHighestPackage;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLogoPicPath() {
        return logoPicPath;
    }

    public  void setLogoPicPath(String logoPicPath) {
        this.logoPicPath = logoPicPath;
    }
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
    private String description;
}
