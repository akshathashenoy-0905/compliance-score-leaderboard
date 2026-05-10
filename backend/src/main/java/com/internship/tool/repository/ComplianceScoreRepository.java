package com.internship.tool.repository;

import com.internship.tool.entity.ComplianceScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {

    List<ComplianceScore> findByEmployeeNameContainingIgnoreCase(String q);

    List<ComplianceScore> findByDeletedFalse();
}