package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.Utils.ChecksumService;
import com.fitgeek.IATestPreparator.Utils.ContentNormalizer;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeSourceServiceImpl implements KnowledgeSourceService {

    private final KnowledgeSourceRepository knowledgeSourceRepository;
    private final UserRepository userRepository;
    private final ContentNormalizer contentNormalizer;
    private final ChecksumService checksumService;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public KnowledgeNormalizedResponseDto createFromText(StrucuturedTextdto textDto, UserDetails userDetails) throws IOException {

        String filename = textDto.subject();

        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));

        String checksum = checksumService.calculateChecksumForDto(textDto);

        Optional<KnowledgeSource> existingDocument = knowledgeSourceRepository.findByIdAndChecksum(owner.getId(), checksum);

        if (existingDocument.isPresent()) {
            return new KnowledgeNormalizedResponseDto(existingDocument.get().getId());
        }

        String normalizedText = contentNormalizer.dtoToMarkdown(textDto);

        Path path = Paths.get(uploadPath + '/' + owner.getId() + '/' + filename);
        Files.write(path, normalizedText.getBytes(StandardCharsets.UTF_8));

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

        Optional<KnowledgeSource> existingDocument = knowledgeSourceRepository.findByIdAndChecksum(owner.getId(), checksum);

        if (existingDocument.isPresent()) {
            return new KnowledgeNormalizedResponseDto(existingDocument.get().getId());
        }

        String normalizedContent = "";

        String storagePath = uploadPath + "/" + owner.getId();
        java.io.File directory = new java.io.File(storagePath);
        if (!directory.exists()) directory.mkdirs();

        String fullPath = storagePath + "/" + filename;
        file.transferTo(new java.io.File(fullPath));

        KnowledgeSource knowledgeSource = KnowledgeSource.builder()
                .owner(owner)
                .sourceType(SourceType.DOCUMENT)
                .normalizedContent(normalizedContent)
                .originalFilename(filename)
                .storagePath(fullPath)
                .checksum(checksum)
                .build();

        KnowledgeSource saved = knowledgeSourceRepository.save(knowledgeSource);

        return new KnowledgeNormalizedResponseDto(
                saved.getId()
        );
    }
}
