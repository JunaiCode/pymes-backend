package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationInDTO;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.QuestionAnswerDTO;
import com.pdg.pymesbackend.dto.out.EvaluationResultOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationMapper evaluationMapper;
    private EvaluationRepository evaluationRepository;
    private QuestionRepository questionRepository;
    private CompanyServiceImpl companyService;
    private RecommendationRepository recommendationRepository;
    private EvaluationResultServiceImpl evaluationResultService;
    private DimensionResultMapper dimensionResultMapper;

    @Override
    public Evaluation save(EvaluationDTO evaluationDTO, String companyId) {
        Evaluation evaluation = evaluationMapper.fromEvaluationDTO(evaluationDTO);
        evaluation.setEvaluationId(UUID.randomUUID().toString());
        evaluation.setQuestionResults(List.of());
        evaluation.setDimensionResults(List.of());
        evaluation.setCompleted(false);
        Evaluation newEvaluation = evaluationRepository.save(evaluation);
        companyService.addEvaluationToCompany(companyId, newEvaluation.getEvaluationId());
        return newEvaluation;
    }

    @Override
    public Map<String, List<EvaluationResultOutDTO>> getEvaluationResults(String evaluationId) {
        Evaluation evaluation = getEvaluationById(evaluationId);
        //get results
        List<EvaluationResult> evaluationResult = evaluationResultService.getEvaluationResults(evaluation.getQuestionResults());
        //organize by dimension
        Map<String, List<EvaluationResult>> resultsByDimension = evaluationResult
                .stream()
                .collect(Collectors.groupingBy(EvaluationResult::getDimensionId));

        //map them to out entity

        return resultsByDimension.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .stream().map(evaluationResultService::mapToOutDTO).toList()));
    }

    public Evaluation getEvaluationById(String evaluationId) {
        return evaluationRepository.findById(evaluationId).orElseThrow(() -> new PymeException(PymeExceptionType.EVALUATION_NOT_FOUND));
    }

    @Override
    public EvaluationResult addEvaluationResult(String evaluationId, EvaluationResultDTO answer) {
        Evaluation evaluation = getEvaluationById(evaluationId);
        EvaluationResult evaluationResult = evaluationResultService.save(answer);
        evaluation.getQuestionResults().add(evaluationResult.getEvaluationResultId());
        evaluationRepository.save(evaluation);
        return evaluationResult;
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

        /*ActionPlan actionPlan = ActionPlan.builder()
                .actionPlanId(UUID.randomUUID().toString())
                .recommendations(recommendationActionPlans)
                .build();*/

        //evaluation.setActionPlanId(actionPlan.getActionPlanId());

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
                String recommendationId = question.get().getRecommendation().getRecommendationId();
                recommendations.add(recommendationRepository.findById(recommendationId).orElseThrow(
                        () -> new PymeException(PymeExceptionType.RECOMMENDATION_NOT_FOUND)
                ));
            }
        }

        return recommendations;
    }

    private List<RecommendationActionPlan> mapToRecommendationActionPlan(List<Recommendation> recommendations){
        List<RecommendationActionPlan> recommendationActionPlans = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            RecommendationActionPlan recommendationActionPlan = new RecommendationActionPlan();
            recommendationActionPlan.setRecommendationActionPlanId(UUID.randomUUID().toString());
            //recommendationActionPlan.setRecommendation(recommendation);
            recommendationActionPlan.setCompleted(false);
            recommendationActionPlans.add(recommendationActionPlan);
        }
        return recommendationActionPlans;
    }

}
