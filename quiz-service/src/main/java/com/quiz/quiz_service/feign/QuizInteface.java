package com.quiz.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.QuizResponse;

@FeignClient("QUESTION-SERVICE")
public interface QuizInteface {

    @GetMapping("/question/generate")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String difficulty, @RequestParam Integer NumQ);
 
    @GetMapping("/question/getQuestions/{ids}")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@PathVariable List<Integer> ids);

    @PostMapping("/question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses);
}
