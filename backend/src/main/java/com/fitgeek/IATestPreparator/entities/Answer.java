package com.fitgeek.IATestPreparator.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @Column(nullable=false, length=100)
    private String label;
    @Column(nullable=false)
    private boolean isCorrect;
    @Column(nullable=false)
    private int orderIndex;
}
