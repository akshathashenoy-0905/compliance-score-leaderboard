package com.internship.tool.controller;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ComplianceScoreController {

    @Autowired
    private ComplianceScoreRepository repository;

    // GET ALL (only non-deleted)
    @GetMapping("/all")
    public List<ComplianceScore> getAll() {
        return repository.findByDeletedFalse();
    }

    // CREATE
    @PostMapping
    public ComplianceScore create(@RequestBody ComplianceScore score) {
        return repository.save(score);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ComplianceScore update(@PathVariable Long id, @RequestBody ComplianceScore score) {
        ComplianceScore existing = repository.findById(id).orElseThrow();

        existing.setEmployeeName(score.getEmployeeName());
        existing.setDepartment(score.getDepartment());
        existing.setScore(score.getScore());
        existing.setStatus(score.getStatus());

        return repository.save(existing);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        ComplianceScore existing = repository.findById(id).orElseThrow();
        existing.setDeleted(true);
        repository.save(existing);
        return "Deleted successfully";
    }

    // SEARCH
    @GetMapping("/search")
    public List<ComplianceScore> search(@RequestParam String q) {
        return repository.findByEmployeeNameContainingIgnoreCase(q);
    }

    // STATS
    @GetMapping("/stats")
    public String stats() {
        List<ComplianceScore> list = repository.findByDeletedFalse();

        long total = list.size();
        long low = list.stream().filter(c -> c.getScore() < 50).count();
        long good = list.stream().filter(c -> c.getScore() >= 80).count();
        double avg = list.stream().mapToInt(ComplianceScore::getScore).average().orElse(0);

        return "Compliance Stats Dashboard\n" +
                "Total Records: " + total + "\n\n" +
                "Average Score: " + (int) avg + "\n\n" +
                "Low Compliance: " + low + "\n\n" +
                "Good Compliance: " + good;
    }

    // EXPORT CSV
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=compliance.csv");

        List<ComplianceScore> list = repository.findByDeletedFalse();

        PrintWriter writer = response.getWriter();
        writer.println("ID,Employee Name,Department,Score,Status");

        for (ComplianceScore c : list) {
            writer.println(
                    c.getId() + "," +
                            c.getEmployeeName() + "," +
                            c.getDepartment() + "," +
                            c.getScore() + "," +
                            c.getStatus()
            );
        }

        writer.flush();
        writer.close();
    }

    // FILE UPLOAD (Swagger shows Choose File)
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadFile(@RequestPart("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return "File is empty";
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.equalsIgnoreCase("text/csv")) {
            return "Only CSV files are allowed";
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            return "File too large (max 2MB)";
        }

        return "File uploaded successfully: " + file.getOriginalFilename();
    }
}