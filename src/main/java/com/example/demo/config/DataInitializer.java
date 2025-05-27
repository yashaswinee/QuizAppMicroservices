package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Wait a bit for the database to be ready
        Thread.sleep(2000);
        
        try {
            // Check if data already exists
            Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM question", Long.class);
            
            if (count == null || count == 0) {
                System.out.println("No questions found. Loading sample data...");
                loadSampleData();
                System.out.println("Sample data loaded successfully!");
            } else {
                System.out.println("Database already contains " + count + " questions. Skipping data initialization.");
            }
        } catch (Exception e) {
            System.out.println("Loading sample data for the first time...");
            loadSampleData();
            System.out.println("Sample data loaded successfully!");
        }
    }

    private void loadSampleData() {
        try {
            // Load and execute the SQL script
            ClassPathResource resource = new ClassPathResource("sql/data/sample_questions.sql");
            byte[] binaryData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sqlScript = new String(binaryData, StandardCharsets.UTF_8);
            
            // Split by semicolon and execute each statement
            String[] sqlStatements = sqlScript.split(";");
            for (String statement : sqlStatements) {
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty() && !trimmedStatement.startsWith("--")) {
                    try {
                        jdbcTemplate.execute(trimmedStatement);
                    } catch (Exception e) {
                        System.out.println("Skipping statement (might be duplicate): " + trimmedStatement.substring(0, Math.min(50, trimmedStatement.length())) + "...");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}