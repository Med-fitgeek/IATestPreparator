package com.fitgeek.IATestPreparator.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answer_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionInstance question;

    @Column(nullable = false, length = 255)
    private String label;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private int orderIndex;
}

