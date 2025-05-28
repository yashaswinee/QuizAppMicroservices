package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
// import lombok.RequiredArgsConstructor;

@Data
@Entity
// @RequiredArgsConstructor
public class QuizResponse {
    
    @Id
    private Integer id;
    private String response;

}
