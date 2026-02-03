package com.fitgeek.IATestPreparator.repositories;

import com.fitgeek.IATestPreparator.entities.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {

}
