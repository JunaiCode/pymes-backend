package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.RecomActionPlanOut;
import com.pdg.pymesbackend.dto.out.StepOutDTO;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.service.modules.ActionPlanConstructor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActionPlanConstructorImpl implements ActionPlanConstructor {

    private TagServiceImpl  tagService;
    private DimensionServiceImpl dimensionService;
    private QuestionServiceImpl questionService;

    @Override
    public List<ActionPlanOutDTO> constructActionPlan(ActionPlan actionPlan) {
        // Primero obtenemos todas las recomendaciones y preguntas necesarias para evitar múltiples llamadas a servicios
        Map<String, Recommendation> recommendationsMap = actionPlan.getRecommendations().stream()
                .collect(Collectors.toMap(Recommendation::getRecommendationId, recommendation -> recommendation));

        Map<String, Question> questionsMap = recommendationsMap.values().stream()
                .map(recommendation -> questionService.getQuestion(recommendation.getQuestionId()))
                .collect(Collectors.toMap(Question::getQuestionId, question -> question));

        Map<String, Tag> tagsMap = questionsMap.values().stream()
                .map(question -> tagService.getTag(question.getTagId()))
                .collect(Collectors.toMap(Tag::getTagId, tag -> tag));

        //map steps in the action plan to the out entity and group them by recommendation

        Map<String, List<StepOutDTO>> stepsByRecommendation = actionPlan.getRecommendationActionPlans().stream()
                .map(step -> StepOutDTO.builder()
                        .recommendationActionPlanId(step.getRecommendationActionPlanId())
                        .description(step.getStep().getDescription())
                        .recommendationId(step.getRecommendationId())
                        .checked(step.isCompleted())
                        .build())
                .collect(Collectors.groupingBy(StepOutDTO::getRecommendationId));

        //group steps by recommendation and map this to the recommendation out entity

        List<RecomActionPlanOut> recomActionPlanOuts = stepsByRecommendation
                .entrySet()
                .stream()
                .map(entry -> {
                    String recommendationId = entry.getKey();
                    List<StepOutDTO> steps = entry.getValue();
                    Recommendation recommendation = recommendationsMap.get(recommendationId);
                    Question question = questionsMap.get(recommendation.getQuestionId());
                    Tag tag = tagsMap.get(question.getTagId());

                    return RecomActionPlanOut.builder()
                            .recommendationId(recommendationId)
                            .description(recommendation.getDescription())
                            .steps(steps)
                            .tagName(tag.getName())
                            .dimensionId(tag.getDimensionId())
                            .build();
                })
                .toList();

        //build the action plan out entity
        return recomActionPlanOuts.stream()
                .collect(Collectors.groupingBy(RecomActionPlanOut::getDimensionId))
                .entrySet().stream()
                .map(entry -> {
                    String dimensionId = entry.getKey();
                    Dimension dimension = dimensionService.get(dimensionId);

                    return ActionPlanOutDTO.builder()
                            .dimensionId(dimensionId)
                            .dimension(dimension.getName())
                            .description(dimension.getDescription())
                            .recommendations(entry.getValue())
                            .build();
                })
                .toList();
    }
}
