package com.fitgeek.IATestPreparator.services;

import com.fitgeek.IATestPreparator.dtos.DocumentResponseDto;
import com.fitgeek.IATestPreparator.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {

    DocumentResponseDto uploadAndSaveFile(MultipartFile file, UserDetails userDetails) throws IOException;

    Page<DocumentResponseDto> retrieveAllDocuments(UserDetails userDetails, Pageable pageable);
}
