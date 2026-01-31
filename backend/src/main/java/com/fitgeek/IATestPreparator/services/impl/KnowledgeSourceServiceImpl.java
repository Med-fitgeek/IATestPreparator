package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.Utils.ChecksumService;
import com.fitgeek.IATestPreparator.Utils.KnowledgeNormalizer;
import com.fitgeek.IATestPreparator.Utils.DocumentExtractor;
import com.fitgeek.IATestPreparator.dtos.KnowledgeNormalizedResponseDto;
import com.fitgeek.IATestPreparator.dtos.StrucuturedTextdto;
import com.fitgeek.IATestPreparator.entities.KnowledgeSource;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.entities.enums.SourceType;
import com.fitgeek.IATestPreparator.excpetion.BusinessException;
import com.fitgeek.IATestPreparator.repositories.KnowledgeSourceRepository;
import com.fitgeek.IATestPreparator.repositories.UserRepository;
import com.fitgeek.IATestPreparator.services.KnowledgeSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeSourceServiceImpl implements KnowledgeSourceService {

    private final KnowledgeSourceRepository knowledgeSourceRepository;
    private final UserRepository userRepository;
    private final KnowledgeNormalizer knowledgeNormalizer;
    private final ChecksumService checksumService;
    private final DocumentExtractor documentExtractor;
    private final StorageServiceImpl storageServiceImpl;

    @Override
    public KnowledgeNormalizedResponseDto createFromText(StrucuturedTextdto textDto, UserDetails userDetails) throws IOException {

        String filename = textDto.subject();

        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));

        //Checking if the file already exist even if the name changes
        String checksum = checksumService.calculateChecksumForDto(textDto);
        Optional<KnowledgeSource> existingDocument = knowledgeSourceRepository.findByOwnerIdAndChecksum(owner.getId(), checksum);
        if (existingDocument.isPresent()) {
            return new KnowledgeNormalizedResponseDto(existingDocument.get().getId());
        }

        //Normalized dto contents to markdown text
        String normalizedText = knowledgeNormalizer.dtoToMarkdown(textDto);

        //Store file and return location path
        Path path = storageServiceImpl.saveText(owner.getId(), filename, normalizedText);

        KnowledgeSource knowledgeSource = KnowledgeSource.builder()
                .owner(owner)
                .sourceType(SourceType.DOCUMENT)
                .normalizedContent(normalizedText)
                .originalFilename(filename)
                .storagePath(String.valueOf(path))
                .checksum(checksum)
                .build();
        KnowledgeSource saved = knowledgeSourceRepository.save(knowledgeSource);

        return new KnowledgeNormalizedResponseDto(
                saved.getId()
        );

    }

    @Override
    public KnowledgeNormalizedResponseDto createFromDocument(MultipartFile file, UserDetails userDetails) throws IOException, NoSuchAlgorithmException {

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

        String checksum = checksumService.calculateChecksumForDocument(file.getInputStream());

        Optional<KnowledgeSource> existingDocument =
                knowledgeSourceRepository.findByOwnerIdAndChecksum(owner.getId(), checksum);

        if (existingDocument.isPresent()) {
            return new KnowledgeNormalizedResponseDto(existingDocument.get().getId());
        }

        String extractedDocument;
        try {
            extractedDocument = documentExtractor.extractRawText(file.getInputStream());
        } catch (Exception e) {
            throw new BusinessException("Failed to extract text from document");
        }

        String normalizedContent = knowledgeNormalizer.rawTextToMarkdown(extractedDocument);

        Path path = storageServiceImpl.saveFile(owner.getId(), filename, file);

        KnowledgeSource knowledgeSource = KnowledgeSource.builder()
                .owner(owner)
                .sourceType(SourceType.DOCUMENT)
                .normalizedContent(normalizedContent)
                .originalFilename(filename)
                .storagePath(String.valueOf(path))
                .checksum(checksum)
                .build();
        KnowledgeSource saved = knowledgeSourceRepository.save(knowledgeSource);

        return new KnowledgeNormalizedResponseDto(
                saved.getId()
        );
    }
}
