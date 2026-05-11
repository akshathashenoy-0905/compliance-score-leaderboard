package com.internship.tool;

import com.internship.tool.entity.ComplianceScore;
import com.internship.tool.repository.ComplianceScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ComplianceScoreRepository repository;

    public DataSeeder(ComplianceScoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        ComplianceScore c1 = new ComplianceScore();
        c1.setEmployeeName("Rahul Sharma");
        c1.setScore(85);
        c1.setStatus("COMPLIANT");

        ComplianceScore c2 = new ComplianceScore();
        c2.setEmployeeName("Anita Rao");
        c2.setScore(72);
        c2.setStatus("PARTIAL");

        ComplianceScore c3 = new ComplianceScore();
        c3.setEmployeeName("Vikram Singh");
        c3.setScore(60);
        c3.setStatus("NON_COMPLIANT");

        repository.saveAll(List.of(c1, c2, c3));
    }
}