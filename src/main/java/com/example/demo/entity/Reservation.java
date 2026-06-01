package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "classroom_id")
    private Long classroomId;
    
    private String semester;
    
    private String campus;
    
    private Integer week;
    
    private Integer dayOfWeek;
    
    private Integer period;
    
    @Column(name = "applicant_name")
    private String applicantName;
    
    @Column(name = "applicant_phone")
    private String applicantPhone;
    
    private String purpose;
    
    private String status;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getClassroomId() {
        return classroomId;
    }
    
    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getCampus() {
        return campus;
    }
    
    public void setCampus(String campus) {
        this.campus = campus;
    }
    
    public Integer getWeek() {
        return week;
    }
    
    public void setWeek(Integer week) {
        this.week = week;
    }
    
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public Integer getPeriod() {
        return period;
    }
    
    public void setPeriod(Integer period) {
        this.period = period;
    }
    
    public String getApplicantName() {
        return applicantName;
    }
    
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    
    public String getApplicantPhone() {
        return applicantPhone;
    }
    
    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
