package com.fitgeek.IATestPreparator.dtos;

import com.fitgeek.IATestPreparator.entities.enums.Status;

import java.time.LocalDateTime;

public record DocumentResponseDto(
        Long id,
        String originalName,
        String documentType,
        Status status,
        LocalDateTime uploadedAt) {}
