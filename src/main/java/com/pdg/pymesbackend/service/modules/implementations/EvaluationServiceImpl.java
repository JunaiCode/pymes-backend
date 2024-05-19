package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationInDTO;
import com.pdg.pymesbackend.dto.QuestionAnswerDTO;
import com.pdg.pymesbackend.mapper.DimensionResultMapper;
import com.pdg.pymesbackend.mapper.EvaluationMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.repository.QuestionRepository;
import com.pdg.pymesbackend.repository.RecommendationRepository;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationMapper evaluationMapper;
    private EvaluationRepository evaluationRepository;
    private QuestionRepository questionRepository;
    private RecommendationRepository recommendationRepository;
    private DimensionResultMapper dimensionResultMapper;

    @Override
    public Evaluation save(EvaluationDTO evaluationDTO) {
        Evaluation evaluation = evaluationMapper.fromEvaluationDTO(evaluationDTO);
        return evaluationRepository.save(evaluation);
    }

    @Override
    public void makeEvaluation(EvaluationInDTO evaluationInDTO) {

        Evaluation evaluation = new Evaluation();
        evaluation.setDate(LocalDateTime.now());
        evaluation.setEvaluationId(UUID.randomUUID().toString());
        evaluation.setDimensionResults(List.of());

        List<QuestionAnswerDTO> answers = new ArrayList<>();
        List<DimensionResult> dimensionResults = evaluationInDTO.getDimensionResults().stream().map(dimensionResultMapper::fromAnswerDTO).toList();

        for (int i = 0; i < evaluationInDTO.getDimensionResults().size(); i++) {
            //validar si la dimension existe
            //validar si el nivel existe
            evaluation.getDimensionResults().add(dimensionResults.get(i));
        }

        List<Recommendation> recommendations = getRecommendations(answers);

        List<RecommendationActionPlan> recommendationActionPlans = mapToRecommendationActionPlan(recommendations);

        ActionPlan actionPlan = ActionPlan.builder()
                .actionPlanId(UUID.randomUUID().toString())
                .recommendations(recommendationActionPlans)
                .build();

        evaluation.setActionPlanId(actionPlan.getActionPlanId());

    }


    private List<Recommendation> getRecommendations(List<QuestionAnswerDTO> evaluationDTO){

        List<Recommendation> recommendations = new ArrayList<>();

        for (QuestionAnswerDTO evaluationResultDTO : evaluationDTO) {
            //obtener pregunta cambiar por usar un servicio
            Optional<Question> question = questionRepository.findById(evaluationResultDTO.getQuestionId());
            boolean passed = false;
            int scorePassed = question.get().getScorePositive();
            if (evaluationResultDTO.getScore() >= scorePassed) {
                passed = true;
            }
            if(passed){
                ArrayList<String> recommendationsIds = (ArrayList<String>) question.get().getRecommendations();
                recommendations.addAll(recommendationRepository.findAllById(recommendationsIds));
            }
        }

        return recommendations;
    }

    private List<RecommendationActionPlan> mapToRecommendationActionPlan(List<Recommendation> recommendations){
        List<RecommendationActionPlan> recommendationActionPlans = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            RecommendationActionPlan recommendationActionPlan = new RecommendationActionPlan();
            recommendationActionPlan.setRecommendationActionPlanId(UUID.randomUUID().toString());
            recommendationActionPlan.setRecommendation(recommendation);
            recommendationActionPlan.setCompleted(false);
            recommendationActionPlans.add(recommendationActionPlan);
        }
        return recommendationActionPlans;
    }

}
