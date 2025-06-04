package com.quiz.quiz_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.quiz_service.dao.QuizDao;
import com.quiz.quiz_service.feign.QuizInteface;
import com.quiz.quiz_service.model.QuestionWrapper;
import com.quiz.quiz_service.model.Quiz;
import com.quiz.quiz_service.model.QuizResponse;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInteface quizInteface;

    public ResponseEntity<String> createQuiz(String difficulty, Integer numQ, String title){
        
        List<Integer> ids = quizInteface.generateQuiz(difficulty, numQ).getBody();
        Quiz quiz = new Quiz();
        
        quiz.setTitle(title);
        quiz.setQuestionIds(ids);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id){
        
        // Optional<Quiz> quiz = quizDao.findById(id);
        List<QuestionWrapper> retQW = new ArrayList<>();
        // List<Question> QDataBase = quiz.get().getQuestion();

        // for(Question tempQW : QDataBase){
        //     QuestionWrapper qw = new QuestionWrapper(tempQW.getId(), tempQW.getQuestionTitle(), tempQW.getOption1(), tempQW.getOption2(), tempQW.getOption3(), tempQW.getOption4());
        //     retQW.add(qw);
        // }

        return new ResponseEntity<>(retQW, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<QuizResponse> reponses) {
        int retScore=0;
        // int index=0;

        // List<Question> questions = quizDao.findById(id).get().getQuestion();

        // for (QuizResponse response : reponses){
            
        //     if (response.getResponse().equals(questions.get(index).getAnswer())){
        //         retScore++;
        //     }
        //     index++;
        // }

        return new ResponseEntity<>(retScore, HttpStatus.OK);

    }
}
