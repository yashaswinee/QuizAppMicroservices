package com.quiz.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.quiz_service.model.CreateDto;
import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.QuizResponse;
import com.quiz.quiz_service.service.QuizService;


@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<List<Integer>>createQuiz(@RequestBody CreateDto data){
        return quizService.createQuiz(data.getDifficulty(), data.getNumQ(), data.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>>getQuiz(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer>getScore(@PathVariable Integer id, @RequestBody List<QuizResponse> reponses){
        return quizService.getScore(id, reponses);
    }

}
