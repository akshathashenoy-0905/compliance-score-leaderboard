package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ComplianceScoreController {

    @Autowired
    private ComplianceScoreRepository repository;

    // GET ALL ACTIVE
    @GetMapping("/all")
    public List<ComplianceScore> getAll() {
        return repository.findByDeletedFalse();
    }

    // SEARCH (SINGLE CLEAN METHOD)
    @GetMapping("/search")
    public List<ComplianceScore> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return repository.search(q, status, from, to);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ComplianceScore update(@PathVariable Long id, @RequestBody ComplianceScore c) {
        ComplianceScore existing = repository.findById(id).orElseThrow();

        existing.setEmployeeName(c.getEmployeeName());
        existing.setScore(c.getScore());
        existing.setDepartment(c.getDepartment());
        existing.setStatus(c.getStatus());

        return repository.save(existing);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ComplianceScore c = repository.findById(id).orElseThrow();
        c.setDeleted(true);
        repository.save(c);
    }

    // STATS (ONLY ACTIVE DATA)
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<ComplianceScore> list = repository.findByDeletedFalse();

        long total = list.size();

        long good = list.stream()
                .filter(x -> "GOOD".equalsIgnoreCase(x.getStatus()))
                .count();

        long low = list.stream()
                .filter(x -> "LOW".equalsIgnoreCase(x.getStatus()))
                .count();

        double avgScore = list.stream()
                .mapToDouble(ComplianceScore::getScore)
                .average()
                .orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("good", good);
        stats.put("low", low);
        stats.put("avgScore", avgScore);

        return stats;
    }
}