package com.fitgeek.IATestPreparator.repositories;

import com.fitgeek.IATestPreparator.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByOwnerId(Long owner_id);
    Optional<Quiz> findByDocumentId(Long document_id);
}
