package com.fitgeek.IATestPreparator.dtos;

import org.springframework.web.multipart.MultipartFile;

public record KnowledgeRequestDto(
        MultipartFile file,
        StrucuturedTextdto strucuturedTextDto
) {}
