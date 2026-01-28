package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.dtos.DocumentResponseDto;
import com.fitgeek.IATestPreparator.entities.Document;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.entities.enums.Status;
import com.fitgeek.IATestPreparator.excpetion.BusinessException;
import com.fitgeek.IATestPreparator.repositories.DocumentRepository;
import com.fitgeek.IATestPreparator.repositories.UserRepository;
import com.fitgeek.IATestPreparator.services.DocumentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public DocumentResponseDto uploadAndSaveFile(MultipartFile file, UserDetails userDetails) throws IOException {

        if (file.isEmpty()) {
            throw new BusinessException("File is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null ||
                !(filename.endsWith(".pdf") || filename.endsWith(".docx") || filename.endsWith(".csv"))) {
            throw new BusinessException("File type not supported");
        }

        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));

        String storagePath = uploadPath + "/" + owner.getId();
        java.io.File directory = new java.io.File(storagePath);
        if (!directory.exists()) directory.mkdirs();

        String fullPath = storagePath + "/" + filename;
        file.transferTo(new java.io.File(fullPath));

        Document document = Document.builder()
                .owner(owner)
                .originalName(filename)
                .storagePath(fullPath)
                .documentType(file.getContentType())
                .status(Status.UPLOADED)
                .build();

        Document saved = documentRepository.save(document);

        return new DocumentResponseDto(
                saved.getId(),
                saved.getOriginalName(),
                saved.getDocumentType(),
                saved.getStatus(),
                saved.getUploadedAt()
        );
    }

    @Override
    public Page<DocumentResponseDto> retrieveAllDocuments(UserDetails userDetails, Pageable pageable) {

        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));

        return documentRepository
                .findByOwnerId(owner.getId(), pageable)
                .map(doc -> new DocumentResponseDto(
                        doc.getId(),
                        doc.getOriginalName(),
                        doc.getDocumentType(),
                        doc.getStatus(),
                        doc.getUploadedAt()
                ));
    }
}
