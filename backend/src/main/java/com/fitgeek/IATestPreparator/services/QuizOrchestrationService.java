package com.fitgeek.IATestPreparator.services;

import com.fitgeek.IATestPreparator.dtos.GeneratedQuizDto;
import com.fitgeek.IATestPreparator.dtos.QuizGenerationRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface  QuizOrchestrationService {
    GeneratedQuizDto generateQuizFromKnowledge(QuizGenerationRequestDto requestDto, UserDetails userDetails);
}
