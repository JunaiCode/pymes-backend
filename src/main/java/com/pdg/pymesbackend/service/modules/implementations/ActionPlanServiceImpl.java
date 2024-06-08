package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.DateDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.ActionPlanMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.ActionPlanRepository;
import com.pdg.pymesbackend.service.modules.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@AllArgsConstructor
public class ActionPlanServiceImpl implements ActionPlanService {

    private ActionPlanRepository actionPlanRepository;
    private ActionPlanMapper actionPlanMapper;
    private EvaluationService evaluationService;
    private QuestionService questionService;
    private ActionPlanConstructor actionPlanConstructor;
    private EvaluationResultService evaluationResultService;


    @Override
    public ActionPlanOutDTO getActualActionPlanByCompanyId(Company company) {
        List<String> evaluations = company.getEvaluations();

        if(evaluations.isEmpty()){
            return null;
        }else {
            Evaluation recent = evaluationService.getRecentCompletedEvaluation(evaluations);
            if (recent == null) {
                return null;
            }
            String actionPlanId = recent.getActionPlanId();
            return actionPlanConstructor.constructActionPlan(findById(actionPlanId));
        }
    }


    @Override
    public ActionPlan findById(String id) {
        return actionPlanRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.ACTION_PLAN_NOT_FOUND));

    }

    @Override
    public ActionPlan save(String evaluationId) {
        Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);

        if(evaluation.isCompleted()){

            ActionPlan fullActionPlan = buildActionPlan(evaluation);
            evaluationService.addActionPlanToEvaluation(evaluationId, fullActionPlan.getActionPlanId());
            return save(fullActionPlan);

        }else {
            throw new PymeException(PymeExceptionType.EVALUATION_NOT_COMPLETED);
        }
    }

    private ActionPlan buildActionPlan(Evaluation evaluation){

        ActionPlan newActionPlan = ActionPlan.builder()
                .actionPlanId(UUID.randomUUID().toString())
                .start(LocalDateTime.now())
                .recommendationActionPlans(List.of())
                .recommendations(List.of())
                .build();

        //obtener todos los resultados

        /*List<EvaluationResult> results = evaluation.getQuestionResults().stream()
                .map(evaluationResultService::findById)
                .toList();*/

        List<EvaluationResult> results = evaluationResultService.getEvaluationResults(evaluation.getQuestionResults());


        //determinar que recomendaciones aplican a la hoja de ruta y
        //crear las entidades para hacer seguimiento de los pasos de las recomendaciones

        List<Recommendation> recommendations = getRecommendations(results);
        List<RecommendationActionPlan> recommendationActionPlans = getRecommendationTracking(recommendations);

        newActionPlan.setRecommendationActionPlans(recommendationActionPlans);
        newActionPlan.setRecommendations(recommendations);
        return newActionPlan;
    }

    private List<Recommendation> getRecommendations(List<EvaluationResult> evaluationResults){

        List<Recommendation> recommendations = new ArrayList<>();

        for (EvaluationResult evaluationResult : evaluationResults) {
            //obtener pregunta
            Question question = questionService.getQuestion(evaluationResult.getQuestionId());
            boolean passed = false;
            //obtener valor de pregunta seleccionada y puntaje de aprobación
            int selected = question.getOptions()
                    .stream()
                    .filter(option -> option.getOptionId().equals(evaluationResult.getOptionId()))
                    .toList()
                    .get(0)
                    .getValue();
            int scorePassed = question.getScorePositive();
            if (selected >= scorePassed) {
                passed = true;
            }
            if(!passed){
                recommendations.add(question.getRecommendation());
            }
        }

        return recommendations;

    }

    private List<RecommendationActionPlan> getRecommendationTracking(List<Recommendation> recommendations){

        return  recommendations
                .stream()
                .flatMap(recommendation -> recommendation.getSteps()
                        .stream()
                        .map(step -> RecommendationActionPlan.builder()
                                .recommendationActionPlanId(UUID.randomUUID().toString())
                                .recommendationId(recommendation.getRecommendationId())
                                .completed(false)
                                .step(step)
                                .date(null)
                                .build()))
                .toList();

    }

    @Override
    public void updateEndDate(DateDTO date, String actionPlanId) {
        ActionPlan actionPlan = findById(actionPlanId);
        try{
            actionPlan.setEnd(LocalDateTime.parse(date.getDate()));
            save(actionPlan);
        }catch (DateTimeParseException e){
            throw new PymeException(PymeExceptionType.INVALID_DATE_FORMAT);
        }
    }

    @Override
    public void updateStepTrack(String actionPlanId, String recommendationActionPlanId, boolean completed) {
        ActionPlan actionPlan = findById(actionPlanId);
        List<RecommendationActionPlan> recommendationActionPlans = actionPlan.getRecommendationActionPlans();
        RecommendationActionPlan recommendationActionPlan = recommendationActionPlans.stream()
                .filter(recommendationActionPlan1 -> recommendationActionPlan1.getRecommendationActionPlanId().equals(recommendationActionPlanId))
                .findFirst()
                .orElseThrow(() -> new PymeException(PymeExceptionType.RECOMMENDATION_ACTION_PLAN_NOT_FOUND));
        recommendationActionPlan.setCompleted(completed);
        recommendationActionPlan.setDate(LocalDateTime.now());
        save(actionPlan);
    }


    private ActionPlan save(ActionPlan actionPlan) {
        return actionPlanRepository.save(actionPlan);
    }
}
