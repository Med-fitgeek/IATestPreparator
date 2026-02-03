package com.fitgeek.IATestPreparator.services;

import com.fitgeek.IATestPreparator.entities.KnowledgeSource;
import com.fitgeek.IATestPreparator.entities.QuizSession;
import com.fitgeek.IATestPreparator.entities.User;

public interface QuizSessionService {

    QuizSession createSession(User user, KnowledgeSource source, int numberOfQuestions, String difficulty);

    void markGenerating(QuizSession session);

    void markGenerated(QuizSession session);

    void markFailed(QuizSession session);
}
