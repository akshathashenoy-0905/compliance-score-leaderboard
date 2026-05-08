package com.internship.tool.service;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceScoreService {

    @Autowired
    private ComplianceScoreRepository repository;

    // GET ALL
    public List<ComplianceScore> getAll() {
        return repository.findAll();
    }
    public ComplianceScore update(Long id, ComplianceScore updated) {
        ComplianceScore existing = repository.findById(id).orElseThrow();
        existing.setEmployeeName(updated.getEmployeeName());
        existing.setScore(updated.getScore());
        existing.setDepartment(updated.getDepartment());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void softDelete(Long id) {
        ComplianceScore existing = repository.findById(id).orElseThrow();
        existing.setDeleted(true);
        repository.save(existing);
    }

    public List<ComplianceScore> search(String name) {
        return repository.search(name, null, null, null);
    }

    public List<ComplianceScore> getAllActive() {
        return repository.findByDeletedFalse();
    }
}