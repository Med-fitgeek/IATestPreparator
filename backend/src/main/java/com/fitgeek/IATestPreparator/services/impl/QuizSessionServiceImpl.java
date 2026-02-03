package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.entities.KnowledgeSource;
import com.fitgeek.IATestPreparator.entities.QuizSession;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.entities.enums.Difficulty;
import com.fitgeek.IATestPreparator.entities.enums.SessionStatus;
import com.fitgeek.IATestPreparator.repositories.KnowledgeSourceRepository;
import com.fitgeek.IATestPreparator.repositories.QuizSessionRepository;
import com.fitgeek.IATestPreparator.repositories.UserRepository;
import com.fitgeek.IATestPreparator.services.QuizGenerationService;
import com.fitgeek.IATestPreparator.services.QuizSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizSessionServiceImpl implements QuizSessionService {

    private final QuizSessionRepository quizSessionRepository;

    @Override
    public QuizSession createSession(User owner, KnowledgeSource source, int numberOfQuestions, String difficulty) {

        QuizSession session = QuizSession.builder()
                .user(owner)
                .source(source)
                .numberOfQuestions(numberOfQuestions)
                .difficulty(Difficulty.valueOf(difficulty))
                .status(SessionStatus.CREATED)
                .build();
        return quizSessionRepository.save(session);
    }

    @Override
    public void markGenerating(QuizSession session) {
        session.setStatus(SessionStatus.GENERATING);
        session.setUpdatedAt(LocalDateTime.now());
        quizSessionRepository.save(session);
    }

    @Override
    public void markGenerated(QuizSession session) {
        session.setStatus(SessionStatus.GENERATED);
        session.setUpdatedAt(LocalDateTime.now());
        quizSessionRepository.save(session);
    }

    @Override
    public void markFailed(QuizSession session) {
        session.setStatus(SessionStatus.FAILED);
        session.setUpdatedAt(LocalDateTime.now());
        quizSessionRepository.save(session);
    }
}
