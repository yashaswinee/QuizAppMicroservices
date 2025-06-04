package com.questionService.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.questionService.dao.QuestionDao;
import com.questionService.model.Question;
import com.questionService.model.QuestionWrapper;
import com.questionService.model.QuizResponse;

@Service
public class QuestionService {
    
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST)  ;
    }

    public ResponseEntity<List<Question>> getByDifficulty(String diff){
        try{
            return new ResponseEntity<> (questionDao.findByDifficulty(diff), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByDifficulty(diff), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity <String> addQuestion(Question question){
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id){
        try{
            questionDao.deleteById(id);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question){
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public List<Question> getCustomQuestions(String difficulty, Integer numQ){
        return questionDao.getCustomQuestions(difficulty, numQ);
    }

    public ResponseEntity<List<Integer>> generateQuiz(String difficuty, Integer numQ) {
        
        return new ResponseEntity<>(questionDao.getQuestionsFromId(difficuty, numQ), HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> ids) {
        List<QuestionWrapper> qw = new ArrayList<>();
        
        for(Integer index: ids){
            Question tempQ = questionDao.findById(index).get();
            
            QuestionWrapper tempQW = new QuestionWrapper(tempQ.getId(), tempQ.getQuestionTitle(), 
                                                        tempQ.getOption1(), tempQ.getOption2(), tempQ.getOption3(), tempQ.getOption4());
            qw.add(tempQW);
        }

        return new ResponseEntity<>(qw, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuizResponse> responses) {
        
        Integer score=0;
        for(QuizResponse response : responses){
            Question temp = questionDao.findById(response.getId()).get();
            if(temp.getAnswer().equals(response.getResponse())){
                score++;
            }
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
    
}
