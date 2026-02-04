import { Difficulty } from "./difficulty.model";

export interface QuizGenerationRequest {
  knowledgeId: number;
  numberOfQuestions: number;
  difficulty: Difficulty;
}
