package com.internship.tool.repository;

import com.internship.tool.entity.ComplianceScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {

    List<ComplianceScore> findByDeletedFalse();

    List<ComplianceScore> findByEmployeeNameContainingIgnoreCaseAndDeletedFalse(String employeeName);

    @Query("""
        SELECT c FROM ComplianceScore c
        WHERE c.deleted = false
        AND (:q IS NULL OR LOWER(c.employeeName) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(c.department) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(c.status) LIKE LOWER(CONCAT('%', :q, '%')))
        AND (:status IS NULL OR c.status = :status)
    """)
    List<ComplianceScore> search(
            @Param("q") String q,
            @Param("status") String status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}