package com.quiz.quiz_service.model;

import lombok.Data;

@Data
public class CreateDto {
    String difficulty;
    Integer numQ;
    String title;
}
