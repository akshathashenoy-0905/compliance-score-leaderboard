package com.internship.tool.repository;

import com.internship.tool.entity.ComplianceScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {

    // Used by your controller
    @Query("SELECT c FROM ComplianceScore c WHERE c.deleted = false")
    List<ComplianceScore> findAllActive();

    @Query("""
SELECT c FROM ComplianceScore c
WHERE c.deleted = false AND
(
LOWER(c.employeeName) LIKE LOWER(CONCAT('%', :q, '%')) OR
LOWER(c.department) LIKE LOWER(CONCAT('%', :q, '%')) OR
LOWER(c.status) LIKE LOWER(CONCAT('%', :q, '%'))
)
""")
    List<ComplianceScore> search(String q);

    // These are required by older controller code
    List<ComplianceScore> findByDeletedFalse();

    List<ComplianceScore> findByEmployeeNameContainingIgnoreCaseAndDeletedFalse(String employeeName);
}