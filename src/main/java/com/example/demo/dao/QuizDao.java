package com.example.demo.dao;

import org.springframework.stereotype.Repository;
import com.example.demo.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{
    
}
