package com.fitgeek.IATestPreparator.Utils;

import com.fitgeek.IATestPreparator.dtos.StrucuturedTextdto;
import org.springframework.stereotype.Component;

@Component
public class ContentNormalizer {

    public String dtoToMarkdown(StrucuturedTextdto dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("# SUBJECT: ").append(dto.subject()).append("\n\n");

        sb.append("## OBJECTIVES:\n").append(dto.objectives()).append("\n\n");

        if (dto.keyConcepts() != null && !dto.keyConcepts().isBlank()) {
            sb.append("## KEY CONCEPTS:\n").append(dto.keyConcepts()).append("\n\n");
        }

        if (dto.additionalNotes() != null && !dto.additionalNotes().isBlank()) {
            sb.append("## ADDITIONAL NOTES:\n").append(dto.additionalNotes()).append("\n");
        }

        return sb.toString();
    }
}
