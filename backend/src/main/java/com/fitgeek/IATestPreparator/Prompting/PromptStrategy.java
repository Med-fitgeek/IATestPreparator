package com.fitgeek.IATestPreparator.Prompting;

import com.fitgeek.IATestPreparator.entities.QuizSession;

public interface PromptStrategy {

    String buildPrompt(QuizSession session);
}
