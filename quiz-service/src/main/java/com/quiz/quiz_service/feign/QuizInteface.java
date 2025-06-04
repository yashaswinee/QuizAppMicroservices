package com.quiz.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.QuizResponse;

@FeignClient("QUESTION-SERVICE")
public interface QuizInteface {

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String difficulty, @RequestParam Integer NumQ);
 
    @GetMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> ids);

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses);
}
