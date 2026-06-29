package com.example.Placement.Models;

import com.example.Placement.Enums.Departments;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    private long registerNumber;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Departments departments;
    private int passedOutYear;
    private int CGPA;
    private String fatherName;
    private String motherName;
    private String fatherOccupation;
    private String motherOccupation;
    private long parentNumber;
    @Column(unique = true)
    private long studentNumber;
    @Column(unique = true)
    private String emailId;
    private String nativePlace;
    @Column(columnDefinition = "LONGBLOB")

    @JsonIgnore
    private byte[] resume;
    private int historyOfArrears;

    public long getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(long registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public int getPassedOutYear() {
        return passedOutYear;
    }

    public void setPassedOutYear(int passedOutYear) {
        this.passedOutYear = passedOutYear;
    }

    public int getCGPA() {
        return CGPA;
    }

    public void setCGPA(int CGPA) {
        this.CGPA = CGPA;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getMotherOccupation() {
        return motherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        this.motherOccupation = motherOccupation;
    }

    public long getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(long parentNumber) {
        this.parentNumber = parentNumber;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public int getHistoryOfArrears() {
        return historyOfArrears;
    }

    public void setHistoryOfArrears(int historyOfArrears) {
        this.historyOfArrears = historyOfArrears;
    }


}
