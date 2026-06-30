package com.example.Placement.Models;

import com.example.Placement.Enums.PlacedStatus;
import jakarta.persistence.*;

@Entity
public class PlacedStudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int PlacedId;

    @ManyToOne
    @JoinColumn(name="registerNumber")
    private Student student;

    @ManyToOne
    @JoinColumn(name="companyId")
    private Company company;
    @Enumerated(EnumType.STRING)
    private PlacedStatus placedstatus;

    private String OfferPath ;

    public int getPlacedId() {
        return PlacedId;
    }

    public void setPlacedId(int placedId) {
        PlacedId = placedId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public PlacedStatus getPlacedstatus() {
        return placedstatus;
    }

    public void setPlacedstatus(PlacedStatus placedstatus) {
        this.placedstatus = placedstatus;
    }

    public String getOfferPath() {
        return OfferPath;
    }

    public void setOfferPath(String offerPath) {
        OfferPath = offerPath;
    }
}
