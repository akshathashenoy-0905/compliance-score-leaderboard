package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ComplianceScoreController {

    @Autowired
    private ComplianceScoreRepository repository;

    // GET ALL
    @GetMapping("/all")
    public List<ComplianceScore> getAll() {
        return repository.findAllActive();
    }

    // SEARCH
    @GetMapping("/search")
    public List<ComplianceScore> search(@RequestParam String q) {
        return repository.search(q);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ComplianceScore update(@PathVariable Long id, @RequestBody ComplianceScore c) {
        c.setId(id);
        return repository.save(c);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ComplianceScore c = repository.findById(id).orElseThrow();
        c.setDeleted(true);
        repository.save(c);
    }

    // STATS API (FIXED - separate method)
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<ComplianceScore> list = repository.findAll();

        long total = list.size();

        long good = list.stream()
                .filter(x -> "GOOD".equals(x.getStatus()))
                .count();

        long low = list.stream()
                .filter(x -> "LOW".equals(x.getStatus()))
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