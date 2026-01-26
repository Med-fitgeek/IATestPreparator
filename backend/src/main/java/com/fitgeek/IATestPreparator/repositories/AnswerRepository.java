package com.fitgeek.IATestPreparator.repositories;

import com.fitgeek.IATestPreparator.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuestionIdOrderByOrderIndexAsc(Long questionId);
}
