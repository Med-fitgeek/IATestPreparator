package com.fitgeek.IATestPreparator.Utils;

import com.fitgeek.IATestPreparator.dtos.StrucuturedTextdto;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.io.IOException;
import java.util.HexFormat;

@Service
public class ChecksumService {

    public String calculateChecksumForDocument(InputStream is) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
        // Conversion du hash en chaîne hexadécimale (disponible depuis Java 17)
        return HexFormat.of().formatHex(digest.digest());
    }

    public String calculateChecksumForDto(StrucuturedTextdto dto) {
        // 1. On crée une chaîne unique qui représente le contenu
        // L'ordre est important pour que le hash soit constant
        String rawContent = dto.subject() + "|" +
                dto.objectives() + "|" +
                (dto.keyConcepts() != null ? dto.keyConcepts() : "") + "|" +
                (dto.additionalNotes() != null ? dto.additionalNotes() : "");

        // 2. On calcule le SHA-256
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawContent.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du calcul du checksum", e);
        }
    }
}