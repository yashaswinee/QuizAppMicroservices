package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired 
    QuestionService questionService;

    public ResponseEntity<String> createQuiz(String difficulty, Integer numQ, String title){
        Quiz quiz = new Quiz();

        List<Question> questions = questionService.getCustomQuestions(difficulty, numQ);

        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    
}
