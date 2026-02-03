package com.fitgeek.IATestPreparator.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedQuiz {
    private List<GeneratedQuestion> questions;
}
