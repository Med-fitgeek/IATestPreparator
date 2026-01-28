package com.fitgeek.IATestPreparator.entities;

import com.fitgeek.IATestPreparator.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz")
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private int numberOfQuestions;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;


}
