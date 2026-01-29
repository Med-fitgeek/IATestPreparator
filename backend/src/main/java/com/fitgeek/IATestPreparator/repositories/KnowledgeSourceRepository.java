package com.fitgeek.IATestPreparator.repositories;

import com.fitgeek.IATestPreparator.entities.KnowledgeSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KnowledgeSourceRepository extends JpaRepository<KnowledgeSource, Long> {

     Optional<KnowledgeSource> findByIdAndChecksum(Long id, String checksum);
}
