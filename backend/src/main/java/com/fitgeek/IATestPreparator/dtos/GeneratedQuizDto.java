package com.fitgeek.IATestPreparator.dtos;

import com.fitgeek.IATestPreparator.pojos.GeneratedQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


public record GeneratedQuizDto (List<GeneratedQuestion> generatedQuestions) {}
