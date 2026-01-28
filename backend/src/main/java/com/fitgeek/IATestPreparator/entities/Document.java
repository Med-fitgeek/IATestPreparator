package com.fitgeek.IATestPreparator.entities;
import com.fitgeek.IATestPreparator.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(nullable = true, length = 50)
    private String originalName;
    @Column(unique=true, nullable=false)
    private String storagePath;
    @Column(length=20)
    private String documentType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
}
