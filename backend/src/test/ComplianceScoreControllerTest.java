package com.internship.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.tool.entity.ComplianceScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ComplianceScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        ComplianceScore score = new ComplianceScore();
        score.setName("Test Company");
        score.setScore(85);
        score.setStatus("ACTIVE");

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(score)))
                .andExpect(status().isOk());
    }

    @Test
    void testSearch() throws Exception {
        mockMvc.perform(get("/api/search?q=test"))
                .andExpect(status().isOk());
    }
}