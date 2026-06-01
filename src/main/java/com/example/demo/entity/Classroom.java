package com.example.demo.entity;
//实体
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "classroom")
public class Classroom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "classroom_no")
    private String classroomNo;
    
    @Column(name = "classroom_name")
    private String classroomName;
    
    private String campus;
    
    private String category;
    
    private Integer seats;
    
    @Column(name = "exam_seats")
    private Integer examSeats;
    
    @Column(name = "building_no")
    private String buildingNo;
    
    @Column(name = "floor_no")
    private String floorNo;
    
    @Column(name = "borrow_type")
    private String borrowType;
    
    private String remark;
    
    private String cdgly;
    
    private String department;
    
    @Column(name = "class_grade")
    private String classGrade;
    
    @Column(name = "sub_category")
    private String subCategory;
    
    private Double area;
    
    @Column(name = "managed_department")
    private String managedDepartment;
    
    @Column(name = "available_weeks")
    private String availableWeeks;
    
    @Column(name = "available_days")
    private String availableDays;
    
    @Column(name = "available_periods")
    private String availablePeriods;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getClassroomNo() {
        return classroomNo;
    }
    
    public void setClassroomNo(String classroomNo) {
        this.classroomNo = classroomNo;
    }
    
    public String getClassroomName() {
        return classroomName;
    }
    
    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
    
    public String getCampus() {
        return campus;
    }
    
    public void setCampus(String campus) {
        this.campus = campus;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getSeats() {
        return seats;
    }
    
    public void setSeats(Integer seats) {
        this.seats = seats;
    }
    
    public Integer getExamSeats() {
        return examSeats;
    }
    
    public void setExamSeats(Integer examSeats) {
        this.examSeats = examSeats;
    }
    
    public String getBuildingNo() {
        return buildingNo;
    }
    
    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }
    
    public String getFloorNo() {
        return floorNo;
    }
    
    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }
    
    public String getBorrowType() {
        return borrowType;
    }
    
    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getCdgly() {
        return cdgly;
    }
    
    public void setCdgly(String cdgly) {
        this.cdgly = cdgly;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getClassGrade() {
        return classGrade;
    }
    
    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }
    
    public String getSubCategory() {
        return subCategory;
    }
    
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    
    public Double getArea() {
        return area;
    }
    
    public void setArea(Double area) {
        this.area = area;
    }
    
    public String getManagedDepartment() {
        return managedDepartment;
    }
    
    public void setManagedDepartment(String managedDepartment) {
        this.managedDepartment = managedDepartment;
    }
    
    public String getAvailableWeeks() {
        return availableWeeks;
    }
    
    public void setAvailableWeeks(String availableWeeks) {
        this.availableWeeks = availableWeeks;
    }
    
    public String getAvailableDays() {
        return availableDays;
    }
    
    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }
    
    public String getAvailablePeriods() {
        return availablePeriods;
    }
    
    public void setAvailablePeriods(String availablePeriods) {
        this.availablePeriods = availablePeriods;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
    
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}