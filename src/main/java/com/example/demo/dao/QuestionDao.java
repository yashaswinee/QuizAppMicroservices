package com.example.demo.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository <Question, Integer>{
     
    List<Question> findByDifficulty(String diff);

    @Query(value = "SELECT * FROM question q WHERE UPPER(q.difficulty)=UPPER(:difficulty) ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> getCustomQuestions(String difficulty, Integer numQ);

}
