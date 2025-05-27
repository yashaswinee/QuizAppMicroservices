package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Question;
import com.example.demo.dao.QuestionDao;

@Service
public class QuestionService {
    
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getByDifficulty(String diff){
        return questionDao.findByDifficulty(diff);
    }

    public String addQuestion(Question question){
        questionDao.save(question);
        return "add: success";
    }

    public String deleteQuestion(Integer id){
        questionDao.deleteById(id);
        return "delete: success";
    }

    public String updateQuestion(Question question){
        questionDao.save(question);
        return "update: success";
    }
    
}
