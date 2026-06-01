package com.example.demo.controller;

import com.example.demo.entity.Classroom;
import com.example.demo.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classrooms")
@CrossOrigin(origins = "*")
public class ClassroomController {
    
    @Autowired
    private ClassroomService classroomService;
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchClassrooms(
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) String campus,
            @RequestParam(required = false) String buildingNo,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subCategory,
            @RequestParam(required = false) Integer minSeats,
            @RequestParam(required = false) Integer maxSeats,
            @RequestParam(required = false) String classroomName,
            @RequestParam(required = false) List<Integer> weeks,
            @RequestParam(required = false) List<Integer> days,
            @RequestParam(required = false) List<Integer> periods,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size) {
        
        // 打印接收到的参数
        System.out.println("========== 查询参数 ==========");
        System.out.println("semester: " + semester);
        System.out.println("campus: " + campus);
        System.out.println("weeks: " + weeks);
        System.out.println("days: " + days);
        System.out.println("periods: " + periods);
        System.out.println("page: " + page + ", size: " + size);
        System.out.println("=============================");
        
        Page<Classroom> classrooms = classroomService.searchClassrooms(
                semester, campus, buildingNo, category, subCategory,
                minSeats, maxSeats, classroomName, weeks, days, periods, page, size);
        
        System.out.println("查询结果数量: " + classrooms.getTotalElements());
        
        Map<String, Object> result = new HashMap<>();
        result.put("data", classrooms.getContent());
        result.put("total", classrooms.getTotalElements());
        result.put("pages", classrooms.getTotalPages());
        result.put("currentPage", classrooms.getNumber());
        result.put("pageSize", classrooms.getSize());
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Object>> reserveClassroom(@RequestBody Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Long classroomId = Long.valueOf(request.get("classroomId").toString());
            String semester = request.get("semester").toString();
            String campus = request.get("campus").toString();
            Integer week = Integer.valueOf(request.get("week").toString());
            Integer dayOfWeek = Integer.valueOf(request.get("dayOfWeek").toString());
            Integer period = Integer.valueOf(request.get("period").toString());
            String applicantName = request.get("applicantName").toString();
            String applicantPhone = request.get("applicantPhone").toString();
            String purpose = request.get("purpose").toString();
            
            classroomService.reserveClassroom(classroomId, semester, campus, 
                week, dayOfWeek, period, applicantName, applicantPhone, purpose);
            
            result.put("success", true);
            result.put("message", "预约成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "预约失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
}
