package com.fitgeek.IATestPreparator.Utils;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class DocumentExtractor {
    private final Tika tika = new Tika();

    public String extractRawText(InputStream inputStream) throws Exception {
        return tika.parseToString(inputStream);
    }
}
