package com.example.Placement.Models;

import com.example.Placement.Enums.Departments;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private Long registerNumber;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Departments departments;

    private int passedOutYear;
    
    @Column(name = "cgpa")  // Explicitly map to database column
    private double cgpa;  // Changed to lowercase for consistency

    private String fatherName;
    private String motherName;

    private String fatherOccupation;
    private String motherOccupation;

    private String parentNumber;

    @Column(unique = true)
    private String studentNumber;

    @Column(unique = true)
    private String emailId;

    private String password;

    private String nativePlace;
    private String resumeUrl;
    private int historyOfArrears;

    // ✅ PROFILE PIC PATH
    private String profilePicPath;

    // ===== GETTERS & SETTERS =====

    public Long getRegisterNumber() { return registerNumber; }
    public void setRegisterNumber(Long registerNumber) { this.registerNumber = registerNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Departments getDepartments() { return departments; }
    public void setDepartments(Departments departments) { this.departments = departments; }

    public int getPassedOutYear() { return passedOutYear; }
    public void setPassedOutYear(int passedOutYear) { this.passedOutYear = passedOutYear; }

    public double getCgpa() { return cgpa; }  // Changed to lowercase
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }  // Changed to lowercase

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getFatherOccupation() { return fatherOccupation; }
    public void setFatherOccupation(String fatherOccupation) { this.fatherOccupation = fatherOccupation; }

    public String getMotherOccupation() { return motherOccupation; }
    public void setMotherOccupation(String motherOccupation) { this.motherOccupation = motherOccupation; }

    public String getParentNumber() { return parentNumber; }
    public void setParentNumber(String parentNumber) { this.parentNumber = parentNumber; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
     public String getPassword() { return password; }
    public void setPassword(String password) { this.password =password ; }

    public String getNativePlace() { return nativePlace; }
    public void setNativePlace(String nativePlace) { this.nativePlace = nativePlace; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public int getHistoryOfArrears() { return historyOfArrears; }
    public void setHistoryOfArrears(int historyOfArrears) { this.historyOfArrears = historyOfArrears; }

    public String getProfilePicPath() { return profilePicPath; }
    public void setProfilePicPath(String profilePicPath) { this.profilePicPath = profilePicPath; }
}