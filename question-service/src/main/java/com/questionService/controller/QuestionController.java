package com.questionService.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.questionService.model.Question;
import com.questionService.model.QuestionWrapper;
import com.questionService.model.QuizResponse;
import com.questionService.service.QuestionService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("question")
public class QuestionController {
    
    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("difficulty/{diff}")
    public ResponseEntity<List<Question>> getByDifficulty(@PathVariable String diff) {
        return questionService.getByDifficulty(diff);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateString(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @GetMapping("/generate") // to generate a quiz -- returns index of questions
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String difficulty, @RequestParam Integer NumQ){
        return questionService.generateQuiz(difficulty, NumQ);
    }
 
    @GetMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> ids){
        return questionService.getQuestions(ids);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses){
        return questionService.getScore(responses);
    }
    
}
