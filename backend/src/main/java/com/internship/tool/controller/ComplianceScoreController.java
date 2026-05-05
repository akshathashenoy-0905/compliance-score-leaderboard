package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@CrossOrigin
public class ComplianceScoreController {

    private final ComplianceScoreRepository repository;

    public ComplianceScoreController(ComplianceScoreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    @GetMapping("/search")
    public List<ComplianceScore> search(@RequestParam String q){
        return repository.search(q);
    }
    public List<ComplianceScore> getAll() {
        return repository.findAllActive();
    }

    @PostMapping("/add")
    public ComplianceScore add(@RequestBody ComplianceScore score) {
        return repository.save(score);
    }

    @PutMapping("/update/{id}")
    public ComplianceScore update(@PathVariable Long id, @RequestBody ComplianceScore newData) {
        ComplianceScore old = repository.findById(id).orElseThrow();
        old.setEmployeeName(newData.getEmployeeName());
        old.setScore(newData.getScore());
        old.setDepartment(newData.getDepartment());
        old.setStatus(newData.getStatus());
        return repository.save(old);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        ComplianceScore item = repository.findById(id).orElseThrow();
        item.setDeleted(true);
        repository.save(item);
        return "Deleted";
    }
}