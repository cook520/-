package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    @Query("SELECT r.classroomId FROM Reservation r WHERE r.semester = :semester " +
           "AND r.campus = :campus AND r.week = :week AND r.dayOfWeek = :dayOfWeek " +
           "AND r.period = :period AND r.status = 'approved'")
    List<Long> findReservedClassroomIds(@Param("semester") String semester,
                                        @Param("campus") String campus,
                                        @Param("week") Integer week,
                                        @Param("dayOfWeek") Integer dayOfWeek,
                                        @Param("period") Integer period);
}
