package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
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

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id){
        
        Optional<Quiz> quiz = quizDao.findById(id);
        List<QuestionWrapper> retQW = new ArrayList<>();
        List<Question> QDataBase = quiz.get().getQuestion();

        for(Question tempQW : QDataBase){
            QuestionWrapper qw = new QuestionWrapper(tempQW.getId(), tempQW.getQuestionTitle(), tempQW.getOption1(), tempQW.getOption2(), tempQW.getOption3(), tempQW.getOption4());
            retQW.add(qw);
        }

        return new ResponseEntity<>(retQW, HttpStatus.OK);
    }
}
