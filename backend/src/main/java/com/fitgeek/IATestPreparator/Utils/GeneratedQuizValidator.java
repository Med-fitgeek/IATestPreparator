package com.fitgeek.IATestPreparator.Utils;

import com.fitgeek.IATestPreparator.excpetion.BusinessException;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuestion;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuiz;

public class GeneratedQuizValidator {

    public static void validate(GeneratedQuiz quiz, int expectedCount) {

        if (quiz == null || quiz.getQuestions() == null || quiz.getQuestions().isEmpty())
            throw new BusinessException("Generated quiz is empty");

        if (quiz.getQuestions().size() != expectedCount)
            throw new BusinessException("Expected " + expectedCount + " questions but got " + quiz.getQuestions().size());

        for (GeneratedQuestion question : quiz.getQuestions()) {

            if (question.getStatement() == null || question.getStatement().isBlank())
                throw new BusinessException("Question statement is empty");

            if (question.getChoices() ==  null || question.getChoices().size() < 2)
                throw new BusinessException("Question must have at least 2 choices");

            if (question.getCorrectIndex() < 0 || question.getCorrectIndex() > question.getChoices().size() -1)
                throw new BusinessException("Correct index out of bounds");

            if (question.getExplanation() == null || question.getExplanation().isBlank())
                throw new BusinessException("Explanation is missing");

            if (question.getSourceQuote() == null || question.getSourceQuote().isBlank())
                throw new BusinessException("Source quote is missing");
        }
    }
}
