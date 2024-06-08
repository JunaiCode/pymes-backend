package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.service.modules.implementations.ActionPlanConstructorImpl;
import com.pdg.pymesbackend.service.modules.implementations.DimensionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.QuestionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionPlanConstructorTest {

    @Mock
    private TagServiceImpl tagService;
    @Mock
    private DimensionServiceImpl dimensionService;
    @Mock
    private QuestionServiceImpl questionService;
    @InjectMocks
    private ActionPlanConstructorImpl actionPlanConstructorImpl;

    @Test
    void testConstructActionPlan(){

        when(questionService.getQuestion("1")).thenReturn(createQuestion());
        when(tagService.getTag("1")).thenReturn(createTag());
        when(dimensionService.get("1")).thenReturn(createDimension());
        ActionPlan actionPlan = createActionPlan();
        ActionPlanOutDTO result = actionPlanConstructorImpl.constructActionPlan(actionPlan);
        assert result != null;
        assertEquals(result.getInfo().size(), 1);
        assertEquals(result.getInfo().get(0).getDimensionId(), "1");
    }

    @Test
    void testConstructActionPlanQuestionNotFound(){

        when(questionService.getQuestion("1")).thenThrow(new PymeException(PymeExceptionType.QUESTION_NOT_FOUND));
        ActionPlan actionPlan = createActionPlan();
        try {
            actionPlanConstructorImpl.constructActionPlan(actionPlan);
        } catch (PymeException e) {
            assert e.getPymeExceptionType().equals(PymeExceptionType.QUESTION_NOT_FOUND);
        }
    }

    @Test
    void testConstructActionPlanTagNotFound(){

        when(questionService.getQuestion("1")).thenReturn(createQuestion());
        when(tagService.getTag("1")).thenThrow(new PymeException(PymeExceptionType.TAG_NOT_FOUND));
        ActionPlan actionPlan = createActionPlan();
        try {
            actionPlanConstructorImpl.constructActionPlan(actionPlan);
        } catch (PymeException e) {
            assert e.getPymeExceptionType().equals(PymeExceptionType.TAG_NOT_FOUND);
        }
    }

    @Test
    void testConstructActionPlanDimensionNotFound(){

        when(questionService.getQuestion("1")).thenReturn(createQuestion());
        when(tagService.getTag("1")).thenReturn(createTag());
        when(dimensionService.get("1")).thenThrow(new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
        ActionPlan actionPlan = createActionPlan();
        try {
            actionPlanConstructorImpl.constructActionPlan(actionPlan);
        } catch (PymeException e) {
            assert e.getPymeExceptionType().equals(PymeExceptionType.DIMENSION_NOT_FOUND);
        }
    }


    ActionPlan createActionPlan(){
        return ActionPlan.builder()
                .actionPlanId("1")
                .start(LocalDateTime.now())
                .end(null)
                .recommendations(List.of(Recommendation.builder()
                        .recommendationId("1")
                        .description("description")
                        .questionId("1")
                        .build()))
                .recommendationActionPlans(List.of(RecommendationActionPlan.builder()
                        .recommendationActionPlanId("1")
                        .recommendationId("1")
                        .step(Step.builder()
                                .description("description")
                                .build())
                        .completed(true)
                        .build()))
                .build();
    }

    Question createQuestion(){
        return Question.builder()
                .questionId("1")
                .tagId("1")
                .build();
    }

    Tag createTag(){
        return Tag.builder()
                .tagId("1")
                .name("tag")
                .dimensionId("1")
                .build();
    }

    Dimension createDimension(){
        return Dimension.builder()
                .dimensionId("1")
                .name("dimension")
                .build();
    }

}
