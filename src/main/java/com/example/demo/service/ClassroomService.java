package com.example.demo.service;

import com.example.demo.entity.Classroom;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ClassroomRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClassroomService {
    
    @Autowired
    private ClassroomRepository classroomRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    public Page<Classroom> searchClassrooms(String semester, String campus, String buildingNo,
                                            String category, String subCategory,
                                            Integer minSeats, Integer maxSeats,
                                            String classroomName,
                                            List<Integer> weeks,
                                            List<Integer> days,
                                            List<Integer> periods,
                                            Integer page, Integer size) {
        
        System.out.println("\n========== Service层接收到的参数 ==========");
        System.out.println("semester: " + semester);
        System.out.println("campus: " + campus);
        System.out.println("weeks: " + weeks);
        System.out.println("days: " + days);
        System.out.println("periods: " + periods);
        System.out.println("page: " + page + ", size: " + size);
        System.out.println("=========================================\n");
        
        Pageable pageable = PageRequest.of(page, size);
        
        Specification<Classroom> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (StringUtils.hasText(campus)) {
                predicates.add(cb.equal(root.get("campus"), campus));
            }
            
            if (StringUtils.hasText(buildingNo) && !"全部".equals(buildingNo)) {
                predicates.add(cb.equal(root.get("buildingNo"), buildingNo));
            }
            
            if (StringUtils.hasText(category) && !"全部".equals(category)) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            
            if (StringUtils.hasText(subCategory) && !"全部".equals(subCategory)) {
                predicates.add(cb.equal(root.get("subCategory"), subCategory));
            }
            
            if (minSeats != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("seats"), minSeats));
            }
            
            if (maxSeats != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("seats"), maxSeats));
            }
            
            if (StringUtils.hasText(classroomName)) {
                predicates.add(cb.or(
                    cb.like(root.get("classroomName"), "%" + classroomName + "%"),
                    cb.like(root.get("classroomNo"), "%" + classroomName + "%")
                ));
            }
            
            // 根据教室的可用时间约束过滤
            // 如果教室设置了可用时间，则检查是否包含选中的时间
            // 如果教室没有设置可用时间（NULL或空），则认为该教室在所有时间都可用
            if (weeks != null && !weeks.isEmpty()) {
                System.out.println("\n[DEBUG] 开始筛选周次: " + weeks);
                List<Predicate> weekPredicates = new ArrayList<>();
                for (Integer week : weeks) {
                    String weekStr = week.toString();
                    System.out.println("  检查周次: " + weekStr);
                    // 精确匹配周次：支持 "1-18" 范围格式和 "1,3,5" 列表格式
                    weekPredicates.add(cb.or(
                        // 范围格式：匹配 "1-18" 中的起始周 "1-"
                        cb.like(root.get("availableWeeks"), weekStr + "-%"),
                        // 范围格式：匹配 "1-18" 中的结束周 "-18"
                        cb.like(root.get("availableWeeks"), "%-" + weekStr),
                        // 范围格式：匹配中间的周次 "-1-"
                        cb.like(root.get("availableWeeks"), "%-" + weekStr + "-%"),
                        // 列表格式：匹配开头的 "1,"
                        cb.like(root.get("availableWeeks"), weekStr + ",%"),
                        // 列表格式：匹配结尾的 ",1"
                        cb.like(root.get("availableWeeks"), "," + weekStr),
                        // 列表格式：匹配中间的 ",1,"
                        cb.like(root.get("availableWeeks"), "," + weekStr + ",%"),
                        // 完全匹配单个周次 "1"
                        cb.equal(root.get("availableWeeks"), weekStr),
                        // 或者字段为空表示无限制（所有周次都可用）
                        cb.isNull(root.get("availableWeeks")),
                        cb.equal(root.get("availableWeeks"), "")
                    ));
                }
                if (!weekPredicates.isEmpty()) {
                    // 使用AND：所有选择的周次都必须满足
                    predicates.add(cb.and(weekPredicates.toArray(new Predicate[0])));
                    System.out.println("[DEBUG] 已添加周次筛选条件\n");
                }
            } else {
                System.out.println("[DEBUG] 未选择周次，跳过筛选\n");
            }
            
            if (days != null && !days.isEmpty()) {
                System.out.println("[DEBUG] 开始筛选星期: " + days);
                List<Predicate> dayPredicates = new ArrayList<>();
                for (Integer day : days) {
                    String dayStr = day.toString();
                    System.out.println("  检查星期: " + dayStr);
                    // 精确匹配星期：支持 "1,2,3,4,5" 列表格式
                    dayPredicates.add(cb.or(
                        // 列表格式：匹配开头的 "1,"
                        cb.like(root.get("availableDays"), dayStr + ",%"),
                        // 列表格式：匹配结尾的 ",1"
                        cb.like(root.get("availableDays"), "," + dayStr),
                        // 列表格式：匹配中间的 ",1,"
                        cb.like(root.get("availableDays"), "," + dayStr + ",%"),
                        // 完全匹配单个星期 "1"
                        cb.equal(root.get("availableDays"), dayStr),
                        // 或者字段为空表示无限制
                        cb.isNull(root.get("availableDays")),
                        cb.equal(root.get("availableDays"), "")
                    ));
                }
                if (!dayPredicates.isEmpty()) {
                    // 使用AND：所有选择的星期都必须满足
                    predicates.add(cb.and(dayPredicates.toArray(new Predicate[0])));
                    System.out.println("[DEBUG] 已添加星期筛选条件\n");
                }
            } else {
                System.out.println("[DEBUG] 未选择星期，跳过筛选\n");
            }
            
            if (periods != null && !periods.isEmpty()) {
                System.out.println("[DEBUG] 开始筛选节次: " + periods);
                List<Predicate> periodPredicates = new ArrayList<>();
                for (Integer period : periods) {
                    String periodStr = period.toString();
                    System.out.println("  检查节次: " + periodStr);
                    // 精确匹配节次：支持 "1,2,3,4,5,6,7,8,9,10" 列表格式
                    periodPredicates.add(cb.or(
                        // 列表格式：匹配开头的 "1,"
                        cb.like(root.get("availablePeriods"), periodStr + ",%"),
                        // 列表格式：匹配结尾的 ",1"
                        cb.like(root.get("availablePeriods"), "," + periodStr),
                        // 列表格式：匹配中间的 ",1,"
                        cb.like(root.get("availablePeriods"), "," + periodStr + ",%"),
                        // 完全匹配单个节次 "1"
                        cb.equal(root.get("availablePeriods"), periodStr),
                        // 或者字段为空表示无限制
                        cb.isNull(root.get("availablePeriods")),
                        cb.equal(root.get("availablePeriods"), "")
                    ));
                }
                if (!periodPredicates.isEmpty()) {
                    // 使用AND：所有选择的节次都必须满足
                    predicates.add(cb.and(periodPredicates.toArray(new Predicate[0])));
                    System.out.println("[DEBUG] 已添加节次筛选条件\n");
                }
            } else {
                System.out.println("[DEBUG] 未选择节次，跳过筛选\n");
            }
            
            // 排除已预约的教室（根据预约表）
            if (weeks != null && !weeks.isEmpty() && 
                days != null && !days.isEmpty() && 
                periods != null && !periods.isEmpty()) {
                
                Set<Long> occupiedIds = new HashSet<>();
                
                // 查询已预约的教室
                for (Integer week : weeks) {
                    for (Integer day : days) {
                        for (Integer period : periods) {
                            List<Long> reservedIds = reservationRepository.findReservedClassroomIds(
                                semester, campus, week, day, period);
                            occupiedIds.addAll(reservedIds);
                        }
                    }
                }
                
                // 排除这些教室
                if (!occupiedIds.isEmpty()) {
                    predicates.add(root.get("id").in(occupiedIds).not());
                }
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return classroomRepository.findAll(spec, pageable);
    }
    
    public void reserveClassroom(Long classroomId, String semester, String campus,
                                 Integer week, Integer dayOfWeek, Integer period,
                                 String applicantName, String applicantPhone, String purpose) {
        Reservation reservation = new Reservation();
        reservation.setClassroomId(classroomId);
        reservation.setSemester(semester);
        reservation.setCampus(campus);
        reservation.setWeek(week);
        reservation.setDayOfWeek(dayOfWeek);
        reservation.setPeriod(period);
        reservation.setApplicantName(applicantName);
        reservation.setApplicantPhone(applicantPhone);
        reservation.setPurpose(purpose);
        reservation.setStatus("approved");
        reservation.setCreatedTime(LocalDateTime.now());
        
        reservationRepository.save(reservation);
    }
}