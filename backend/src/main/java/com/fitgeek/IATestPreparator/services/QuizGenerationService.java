package com.fitgeek.IATestPreparator.services;
import com.fitgeek.IATestPreparator.entities.QuizSession;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuiz;

public interface QuizGenerationService {

    GeneratedQuiz generateQuiz(QuizSession session);
}
