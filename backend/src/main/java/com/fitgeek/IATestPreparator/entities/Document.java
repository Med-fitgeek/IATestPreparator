package com.fitgeek.IATestPreparator.entities;
import com.fitgeek.IATestPreparator.entities.enums.DocumentType;
import com.fitgeek.IATestPreparator.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(nullable = true)
    private String originalName;
    @Column(unique=true, nullable=false)
    private String storagePath;
    @Column(length=20)
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime uploadedAt;
}
