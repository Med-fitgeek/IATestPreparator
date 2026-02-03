package com.fitgeek.IATestPreparator.services.impl;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitgeek.IATestPreparator.Prompting.PromptStrategy;
import com.fitgeek.IATestPreparator.entities.QuizSession;
import com.fitgeek.IATestPreparator.excpetion.BusinessException;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuiz;
import com.fitgeek.IATestPreparator.services.QuizGenerationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class QuizGenerationServiceImpl implements QuizGenerationService {

    private final ChatClient chatClient;
    private final PromptStrategy promptStrategy;

    public QuizGenerationServiceImpl(ChatClient.Builder chatClientBuilder,
                                     PromptStrategy promptStrategy,
                                     ObjectMapper objectMapper) {

        var validationAdvisor = StructuredOutputValidationAdvisor.builder()
                .objectMapper(objectMapper)
                .outputType(new ParameterizedTypeReference<GeneratedQuiz>() {})
                .maxRepeatAttempts(3)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(validationAdvisor)
                .build();
        this.promptStrategy = promptStrategy;
    }

    @Override
    public GeneratedQuiz generateQuiz(QuizSession session) {

        String systemInstructions = promptStrategy.buildPrompt(session);

        try {
            return chatClient.prompt()
                    .system(systemInstructions)
                    .user(session.getSource().getNormalizedContent())
                    // .entity() takes care of: formatting, parsing and mapping
                    .call()
                    .entity(GeneratedQuiz.class);

        } catch (Exception e) {
            // Spring AI lèvera une exception si le retry échoue ou si le format est invalide
            throw new BusinessException("IA could not generate a valide quiz : " + e.getMessage());
        }
    }
}
