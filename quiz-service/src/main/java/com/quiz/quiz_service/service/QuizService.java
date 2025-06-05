package com.quiz.quiz_service.service;

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
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> ids = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> retQW = quizInteface.getQuestions(ids);
        return retQW;
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
