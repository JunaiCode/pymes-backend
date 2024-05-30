package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.DateDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.mapper.ActionPlanMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.ActionPlanRepository;
import com.pdg.pymesbackend.service.modules.ActionPlanConstructor;
import com.pdg.pymesbackend.service.modules.EvaluationResultService;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import com.pdg.pymesbackend.service.modules.QuestionService;
import com.pdg.pymesbackend.service.modules.implementations.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActionPlanServiceTest {
    @InjectMocks
    private ActionPlanServiceImpl actionPlanService;
    @Mock
    private ActionPlanRepository actionPlanRepository;
    @Mock
    private ActionPlanMapper actionPlanMapper;
    @Mock
    private EvaluationService evaluationService;
    @Mock
    private QuestionService questionService;
    @Mock
    private ActionPlanConstructor actionPlanConstructor;
    @Mock
    private EvaluationResultService evaluationResultService;



    @Test
    void testGetActualActionPlanByCompanyId(){
        Company company = createCompany();
        Evaluation evaluation = createEvaluation();
        when(evaluationService.getRecentCompletedEvaluation(company.getEvaluations())).thenReturn(evaluation);
        when(actionPlanRepository.findById("1")).thenReturn(Optional.ofNullable(createActionPlan()));
        lenient().when(actionPlanConstructor.constructActionPlan(any())).thenReturn(ActionPlanOutDTO.builder().build());
        ActionPlanOutDTO result = actionPlanService.getActualActionPlanByCompanyId(company);
        assertNotNull(result);
    }

    @Test
    void testGetActualActionPlanByCompanyIdNoEvaluations(){
        Company company = createCompany();
        when(evaluationService.getRecentCompletedEvaluation(company.getEvaluations())).thenReturn(null);
        ActionPlanOutDTO result = actionPlanService.getActualActionPlanByCompanyId(company);
        assertNull(result);
    }

    @Test
    void testGetPlanById(){
        when(actionPlanRepository.findById("1")).thenReturn(Optional.ofNullable(createActionPlan()));
        ActionPlan result = actionPlanService.findById("1");
        assertNotNull(result);
        assertEquals(result.getActionPlanId(), "1");
    }

    @Test
    void testGetPlanByIdNotFound(){
        when(actionPlanRepository.findById("1")).thenReturn(Optional.empty());
        PymeException exception = assertThrows(PymeException.class, () -> actionPlanService.findById("1"));
        assertEquals(exception.getMessage(), "Action plan not found");
    }

    @Test
    void testSave(){
        Evaluation evaluation = createEvaluation();
        ActionPlan actionPlan = createActionPlan();
        when(evaluationService.getEvaluationById("1")).thenReturn(evaluation);
        when(evaluationResultService.findById("1")).thenReturn(createEvaluationResult());
        when(questionService.getQuestion("1")).thenReturn(createQuestion());
        when(actionPlanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ActionPlan result = actionPlanService.save("1");
        assertNotNull(result);
        assertEquals(1, result.getRecommendations().size());
        assertEquals(2, result.getRecommendationActionPlans().size());
    }

    @Test
    void testSaveEvaluationNotCompleted(){
        Evaluation evaluation = createEvaluation();
        evaluation.setCompleted(false);
        when(evaluationService.getEvaluationById("1")).thenReturn(evaluation);
        PymeException exception = assertThrows(PymeException.class, () -> actionPlanService.save("1"));
        assertEquals(exception.getMessage(), "Evaluation not completed");
    }

    @Test
    void testUpdateEndDate(){
        ActionPlan actionPlan = createActionPlan();
        when(actionPlanRepository.findById("1")).thenReturn(Optional.of(actionPlan));
        ArgumentCaptor<ActionPlan> captor = ArgumentCaptor.forClass(ActionPlan.class);
        actionPlanService.updateEndDate(DateDTO.builder().date("2024-05-29T14:30:00").build(),"1");
        verify(actionPlanRepository, times(1)).save(captor.capture());
        assertNotNull(captor.getValue().getEnd());
        assertEquals(captor.getValue().getEnd(), LocalDateTime.parse("2024-05-29T14:30:00"));
    }

    @Test
    void testUpdateEndDateAPlanNotFound(){
        when(actionPlanRepository.findById("1")).thenReturn(Optional.empty());
        PymeException exception = assertThrows(PymeException.class, () -> actionPlanService.updateEndDate(DateDTO.builder().date("2024-05-29T14:30:00").build(),"1"));
        assertEquals(exception.getMessage(), "Action plan not found");
    }

    @Test
    void testUpdateEndDateInvalidDate(){
        when(actionPlanRepository.findById("1")).thenReturn(Optional.of(createActionPlan()));
        PymeException exception = assertThrows(PymeException.class, () -> actionPlanService.updateEndDate(DateDTO.builder().date("2024-05-29").build(),"1"));
        assertEquals(exception.getMessage(), "Invalid date format");
    }

    @Test
    void testUpdateStepTrack(){
        ActionPlan actionPlan = createActionPlan();
        when(actionPlanRepository.findById("1")).thenReturn(Optional.of(actionPlan));
        ArgumentCaptor<ActionPlan> captor = ArgumentCaptor.forClass(ActionPlan.class);
        actionPlanService.updateStepTrack("1", "1", true);
        verify(actionPlanRepository, times(1)).save(captor.capture());
        assertTrue(captor.getValue().getRecommendationActionPlans().get(0).isCompleted());
    }

    @Test
    void testUpdateStepTrackAPlanNotFound(){
        when(actionPlanRepository.findById("1")).thenReturn(Optional.of(createActionPlan()));
        try {
            actionPlanService.updateStepTrack("1", "2", true);
        } catch (PymeException e) {
            assertEquals(e.getMessage(), "Recommendation action plan not found");
        }
    }

    Company createCompany(){
        return Company.builder()
                .companyId("1")
                .companyType(CompanyType.builder()
                        .companyTypeId("MICRO")
                        .description("MicroEmpresa")
                        .description("Menos de 10 empleados")
                        .build())
                .employees(20)
                .address("Carrera24")
                .name("Emcali")
                .economicSectorId("4")
                .cityId("1").nit("2032030345")
                .legalRep("Juan")
                .legalRepEmail("juan@hotmail.com")
                .legalRepTel("1234567892")
                .tel("9876543210")
                .creationDate(LocalDateTime.now())
                .evaluations(List.of("1")).build();
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
                        .completed(false)
                        .build()))
                .build();
    }

    Evaluation createEvaluation(){
        return Evaluation.builder()
                .evaluationId("1")
                .dimensionResults(List.of())
                .questionResults(List.of("1"))
                .date(LocalDateTime.now())
                .completed(true)
                .actionPlanId("1")
                .build();
    }

    EvaluationResult createEvaluationResult(){
        return EvaluationResult.builder()
                .evaluationResultId("1")
                .questionId("1")
                .optionId("1")
                .dimensionId("1")
                .marked(true)
                .build();
    }

    Question createQuestion(){
        return Question.builder()
                .questionId("1")
                .question("Question 1")
                .weight(1.0)
                .scorePositive(2)
                .options(List.of(
                        Option.builder()
                                .optionId("1")
                                .description("Option 1")
                                .value(1)
                                .build(),
                        Option.builder()
                                .optionId("2")
                                .description("Option 2")
                                .value(2)
                                .build()))
                .recommendation(Recommendation.builder()
                        .description("Recommendation 1")
                        .steps(List.of(
                                Step.builder().description("Step 1").build(),
                                Step.builder().description("Step 2").build()
                        ))
                        .build())
                .dimensionId("1")
                .tagId("1")
                .companyTypeId("1")
                .build();
    }

}
