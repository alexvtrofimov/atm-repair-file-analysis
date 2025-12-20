package com.example.atm.repository;

import com.example.atm.controller.dto.ReasonCountDto;
import com.example.atm.controller.dto.ReasonDurationDto;
import com.example.atm.controller.dto.ReasonRepeatInterfaceDto;
import com.example.atm.entity.AtmRepairReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmRepairReasonRepository extends JpaRepository<AtmRepairReason, String> {
    @Query("SELECT new com.example.atm.controller.dto.ReasonCountDto(a.reason, count(a)) " +
            "FROM AtmRepairReason a " +
            "GROUP BY a.reason " +
            "ORDER BY count(a) DESC " +
            "LIMIT 3")
    List<ReasonCountDto> getTop3Reason();

    @Query("SELECT new com.example.atm.controller.dto.ReasonDurationDto(a.caseId, a.reason, a.timeEnd - a.timeBegin) " +
            "FROM AtmRepairReason a " +
            "ORDER BY a.timeEnd - a.timeBegin DESC " +
            "LIMIT 3")
    List<ReasonDurationDto> getTop3Duration();

    @Query(value = "SELECT " +
            "a1.case_id as caseId, " +
            "a1.reason as reason, " +
            "count(a1.reason) as count " +
            "FROM atm_repair_reason AS a1 " +
            "JOIN atm_repair_reason AS a2 ON a1.reason = a2.reason AND a1.case_id != a2.case_id " +
            "WHERE " +
            "(a2.time_begin >= a1.time_begin AND a2.time_begin <= a1.time_end + (:days * INTERVAL '1 day')) " +
            "OR " +
            "(a2.time_end >= a1.time_begin AND a2.time_end <= a1.time_end + (:days * INTERVAL '1 day')) " +
            "GROUP BY a1.case_id, a1.reason " +
            "ORDER BY count(a1.reason) DESC", nativeQuery = true)
    List<ReasonRepeatInterfaceDto> getRepeatRepairs(int days);
}
