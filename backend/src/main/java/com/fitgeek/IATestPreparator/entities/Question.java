package com.fitgeek.IATestPreparator.entities;

import com.fitgeek.IATestPreparator.entities.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(nullable = false, length = 250)
    private String statement;
    @Column(nullable = false,  length = 20)
    private QuestionType questionType;
    @Column(nullable = false)
    private int orderIndex;


}
