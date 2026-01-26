package com.fitgeek.IATestPreparator.repositories;

import com.fitgeek.IATestPreparator.entities.Document;
import com.fitgeek.IATestPreparator.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByOwnerId(Long owner_id);
    Optional<Document> findByStatus(Status status);
}
