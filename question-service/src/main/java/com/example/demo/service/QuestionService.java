package com.example.demo.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Question;

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
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
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
    
}
