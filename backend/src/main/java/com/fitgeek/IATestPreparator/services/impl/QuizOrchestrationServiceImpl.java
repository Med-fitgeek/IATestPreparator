package com.fitgeek.IATestPreparator.services.impl;

import com.fitgeek.IATestPreparator.dtos.GeneratedQuizDto;
import com.fitgeek.IATestPreparator.dtos.QuizGenerationRequestDto;
import com.fitgeek.IATestPreparator.entities.KnowledgeSource;
import com.fitgeek.IATestPreparator.entities.QuizSession;
import com.fitgeek.IATestPreparator.entities.User;
import com.fitgeek.IATestPreparator.excpetion.BusinessException;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuestion;
import com.fitgeek.IATestPreparator.pojos.GeneratedQuiz;
import com.fitgeek.IATestPreparator.repositories.KnowledgeSourceRepository;
import com.fitgeek.IATestPreparator.repositories.UserRepository;
import com.fitgeek.IATestPreparator.services.QuizGenerationService;
import com.fitgeek.IATestPreparator.services.QuizOrchestrationService;
import com.fitgeek.IATestPreparator.services.QuizSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizOrchestrationServiceImpl implements QuizOrchestrationService {

    private final KnowledgeSourceRepository knowledgeSourceRepository;
    private final QuizSessionService quizSessionService;
    private final QuizGenerationService quizGenerationService;
    private final UserRepository userRepository;

    @Override
    public GeneratedQuizDto generateQuizFromKnowledge(
            QuizGenerationRequestDto requestDto,
            UserDetails userDetails
    ) {
        // 1. Vérifier que la source de connaissance existe et appartient à l'utilisateur
        KnowledgeSource knowledgeSource = knowledgeSourceRepository
                .findById(requestDto.knowledgeId())
                .orElseThrow(() -> new BusinessException("KnowledgeSource not found"));

        User owner = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!knowledgeSource.getOwner().getId().equals(owner.getId())) {
            throw new BusinessException("You do not own this knowledge source");
        }

        // 2. Créer la session de quiz (status = CREATED)
        QuizSession session = quizSessionService.createSession(
                owner,
                knowledgeSource,
                requestDto.numberOfQuestions(),
                requestDto.difficulty()
        );

        try {
            // 3. Passer la session en GENERATING
            quizSessionService.markGenerating(session);

            // 4. Générer le quiz via l’IA
            GeneratedQuiz generatedQuiz = quizGenerationService.generateQuiz(session);

            // 5. Passer la session en GENERATED
            quizSessionService.markGenerated(session);

            // 6. Mapper vers DTO pour l’API
            return mapToDto(generatedQuiz);

        } catch (Exception e) {
            // 7. En cas d’erreur, marquer la session en FAILED
            quizSessionService.markFailed(session);
            throw new BusinessException("Failed to generate quiz: " + e.getMessage());
        }
    }

    private GeneratedQuizDto mapToDto(GeneratedQuiz generatedQuiz) {
        // on récupère directement la liste des GeneratedQuestion de l'objet GeneratedQuiz
        List<GeneratedQuestion> questions = generatedQuiz.getQuestions();

        // on instancie le DTO avec cette liste
        return new GeneratedQuizDto(questions);
    }

}
