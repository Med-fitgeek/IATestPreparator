package com.fitgeek.IATestPreparator.controllers;

import com.fitgeek.IATestPreparator.dtos.DocumentResponseDto;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DocumentResponseDto> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

        DocumentResponseDto response =
                documentService.uploadAndSaveFile(file, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<DocumentResponseDto> getDocuments(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("uploadedAt").descending());
        return documentService.retrieveAllDocuments(userDetails, pageable);
    }
}
