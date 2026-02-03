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
public class GeneratedQuestion {
    private String statement;               // Enoncé de la question
    private List<String> choices;  // Liste des propositions
    private int correctIndex;               // Index de la bonne réponse dans choices
    private String explanation;             // Explication détaillée
    private String sourceQuote;             // Citation issue du document ou texte
}