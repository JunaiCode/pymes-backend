package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.modules.EvaluationResultService;
import com.pdg.pymesbackend.service.modules.QuestionService;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.modules.implementations.EvaluationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationServiceTest {
    @Mock
    private EvaluationRepository evaluationRepository;
    @Mock
    private QuestionService questionService;
    @Mock
    private EvaluationResultService evaluationResultService;
    @Mock
    private VersionService versionService;
    @InjectMocks
    private EvaluationServiceImpl evaluationService;

    @Test
    void testSave() {
        when(evaluationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Evaluation result = evaluationService.save("companyId");
        verify(evaluationRepository, times(1)).save(any());
        assertNotNull(result);
        assertFalse(result.isCompleted());
        assertEquals(0, result.getQuestionResults().size());
    }

    @Test
    void testGetEvaluationById() {
        Evaluation evaluation = createEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        Evaluation result = evaluationService.getEvaluationById(evaluation.getEvaluationId());
        verify(evaluationRepository, times(1)).findById(evaluation.getEvaluationId());
        assertNotNull(result);
    }

    @Test
    void testGetEvaluationByIdNotFound() {
        when(evaluationRepository.findById("evaluationId")).thenReturn(Optional.empty());
        try {
            evaluationService.getEvaluationById("evaluationId");
        } catch (Exception e) {
            verify(evaluationRepository, times(1)).findById("evaluationId");
            assertEquals("Evaluation not found", e.getMessage());
        }

    }

    @Test
    void testFinishEvaluationNotCompleted(){
        Evaluation evaluation = createEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        when(evaluationResultService.getEvaluationResults(any())).thenReturn(List.of(EvaluationResult.builder().optionId(null).build()));
        try {
            evaluationService.finishEvaluation(evaluation.getEvaluationId(), "versionId");
        } catch (Exception e) {
            verify(evaluationRepository, times(1)).findById(evaluation.getEvaluationId());
            verify(evaluationResultService, times(1)).getEvaluationResults(any());
            assertEquals("Evaluation not completed", e.getMessage());
        }

    }

    @Test
    void testFinishEvaluation(){
        Evaluation evaluation = createEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));

        //set version
        when(versionService.get(any())).thenReturn(Version.builder().dimensions(List.of(createDimension(), createDimension2(), createDimension3())).build());

        //set evaluationResults
        when(evaluationResultService.getEvaluationResults(evaluation.getQuestionResults())).thenReturn(createEvaluationResults());

        //set questions
        when(questionService.getQuestion("1")).thenReturn(createQuestion("1", "dimensionId1"));
        when(questionService.getQuestion("2")).thenReturn(createQuestion("2", "dimensionId1"));
        when(questionService.getQuestion("7")).thenReturn(createQuestion("7", "dimensionId2"));
        when(questionService.getQuestion("8")).thenReturn(createQuestion("8", "dimensionId2"));
        when(questionService.getQuestion("9")).thenReturn(createQuestion("9", "dimensionId2"));
        when(questionService.getQuestion("10")).thenReturn(createQuestion("10", "dimensionId2"));
        when(questionService.getQuestion("13")).thenReturn(createQuestion("13", "dimensionId3"));
        when(questionService.getQuestion("14")).thenReturn(createQuestion("14", "dimensionId3"));
        when(questionService.getQuestion("15")).thenReturn(createQuestion("15", "dimensionId3"));
        when(questionService.getQuestion("16")).thenReturn(createQuestion("16", "dimensionId3"));
        when(questionService.getQuestion("17")).thenReturn(createQuestion("17", "dimensionId3"));
        when(questionService.getQuestion("18")).thenReturn(createQuestion("18", "dimensionId3"));
        evaluationService.finishEvaluation(evaluation.getEvaluationId(), "versionId");
        verify(evaluationRepository, times(1)).findById(evaluation.getEvaluationId());
        verify(evaluationResultService, times(1)).getEvaluationResults(evaluation.getQuestionResults());
        verify(versionService, times(1)).get("versionId");
        verify(questionService, times(12)).getQuestion(any());
        assertTrue(evaluation.isCompleted());
        assertEquals(3, evaluation.getDimensionResults().size());
        assertEquals(1, evaluation.getDimensionResults().get(0).getLevelValue());
        assertEquals(2, evaluation.getDimensionResults().get(1).getLevelValue());
        assertEquals(3, evaluation.getDimensionResults().get(2).getLevelValue());
    }

    @Test
    void testGetCompletedEvaluations() {
        Evaluation evaluation = createCompletedEvaluation();
        when(evaluationRepository.findAllByEvaluationIdAndCompleted(List.of(evaluation.getEvaluationId()), true)).thenReturn(List.of(evaluation));
        List<Evaluation> result = evaluationService.getCompletedEvaluationsByIds(List.of(evaluation.getEvaluationId()));
       verify(evaluationRepository, times(1)).findAllByEvaluationIdAndCompleted(List.of(evaluation.getEvaluationId()), true);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCompletedEvaluationsEmpty() {
        when(evaluationRepository.findAllByEvaluationIdAndCompleted(List.of("evaluationId"), true)).thenReturn(List.of());
        List<Evaluation> result = evaluationService.getCompletedEvaluationsByIds(List.of("evaluationId"));
        verify(evaluationRepository, times(1)).findAllByEvaluationIdAndCompleted(List.of("evaluationId"), true);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testAddEvaluationResults(){
        Evaluation evaluation = createEvaluation();
        List<EvaluationResult> evaluationResults = createEvaluationResults();
        List<EvaluationResultDTO> evaluationResultsDTO = createEvaluationResultsDTO();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        when(evaluationResultService.saveAll(evaluationResultsDTO)).thenReturn(evaluationResults);
        ArgumentCaptor<Evaluation> captor = ArgumentCaptor.forClass(Evaluation.class);
        evaluationService.addEvaluationResults(evaluation.getEvaluationId(), evaluationResultsDTO);
        verify(evaluationRepository, times(1)).save(captor.capture());
        assertEquals(evaluationResults.size(), captor.getValue().getQuestionResults().size());
        assertEquals(evaluationResults.get(0).getEvaluationResultId(), captor.getValue().getQuestionResults().get(0));
    }

    @Test
    void testCheckUncompletedEvaluation(){
        Evaluation evaluation = createEvaluation();
        EvaluationResult answer1 = createEvaluationResult("1", "1", "dimensionId1", 1);
        EvaluationResult answer2 = createEvaluationResult("2", "2", "dimensionId1", 0);
        QuestionOutDTO question1 = QuestionOutDTO.builder().questionId("1").build();
        QuestionOutDTO question2 = QuestionOutDTO.builder().questionId("2").build();

        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        when(evaluationResultService.getEvaluationResults(evaluation.getQuestionResults()))
                .thenReturn(List.of(answer1, answer2));
        when(questionService.mapAnswerToQuestionOutDTO(answer1)).thenReturn(question1);
        when(questionService.mapAnswerToQuestionOutDTO(answer2)).thenReturn(question2);
        OnGoingEvaluationOutDTO result = evaluationService.checkUncompletedEvaluation(Company.builder().evaluations(List.of("1", evaluation.getEvaluationId())).build());
        verify(evaluationRepository, times(1)).findById(evaluation.getEvaluationId());
        verify(evaluationResultService, times(1)).getEvaluationResults(evaluation.getQuestionResults());
        verify(questionService, times(2)).mapAnswerToQuestionOutDTO(any());
        assertNotNull(result);
        assertEquals(evaluation.getEvaluationId(), result.getEvaluationId());
        assertEquals(2, result.getQuestions().size());
    }

    @Test
    void testCheckUncompletedEvaluationEmpty(){
        OnGoingEvaluationOutDTO result = evaluationService.checkUncompletedEvaluation(Company.builder().evaluations(List.of()).build());
        assertNull(result);
    }

    @Test
    void testCheckUncompletedEvaluationFalse(){
        Evaluation evaluation = createCompletedEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        OnGoingEvaluationOutDTO result = evaluationService.checkUncompletedEvaluation(Company.builder().evaluations(List.of(evaluation.getEvaluationId())).build());
        assertNull(result);
    }

    @Test
    void testGetRecentCompletedEvaluation(){
        Evaluation evaluation = createCompletedEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        Evaluation result = evaluationService.getRecentCompletedEvaluation(List.of(evaluation.getEvaluationId(), "evaluationId"));
        verify(evaluationRepository, times(1)).findById(evaluation.getEvaluationId());
        assertNotNull(result);
        assertEquals(evaluation.getEvaluationId(), result.getEvaluationId());
    }

    @Test
    void testGetRecentCompletedEvaluationEmpty(){
        Evaluation result = evaluationService.getRecentCompletedEvaluation(List.of());
        assertNull(result);
    }

    @Test
    void testGetCompletedEvaluationsByIds(){
        Evaluation evaluation = createCompletedEvaluation();
        when(evaluationRepository.findAllByEvaluationIdAndCompleted(List.of(evaluation.getEvaluationId()), true)).thenReturn(List.of(evaluation));
        List<Evaluation> result = evaluationService.getCompletedEvaluationsByIds(List.of(evaluation.getEvaluationId()));
        verify(evaluationRepository, times(1)).findAllByEvaluationIdAndCompleted(List.of(evaluation.getEvaluationId()), true);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCompletedEvaluationsByIdsEmpty(){
        List<Evaluation> result = evaluationService.getCompletedEvaluationsByIds(List.of());
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testAddActionPlan(){
        Evaluation evaluation = createEvaluation();
        when(evaluationRepository.findById(evaluation.getEvaluationId())).thenReturn(Optional.of(evaluation));
        ArgumentCaptor<Evaluation> captor = ArgumentCaptor.forClass(Evaluation.class);
        evaluationService.addActionPlanToEvaluation(evaluation.getEvaluationId(), "actionPlanId");
        verify(evaluationRepository, times(1)).save(captor.capture());
        assertEquals("actionPlanId", captor.getValue().getActionPlanId());
    }


    Evaluation createEvaluation(){
        return Evaluation.builder()
                .evaluationId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .questionResults(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
                .dimensionResults(List.of())
                .completed(false)
                .build();
    }

    List<EvaluationResult> createEvaluationResults(){

        EvaluationResult dimension1level1question1 = createEvaluationResult("1", "1", "dimensionId1", 1);
        EvaluationResult dimension1level1question2 = createEvaluationResult("2", "2", "dimensionId1", 0);
        EvaluationResult dimension2level1question1 = createEvaluationResult("3", "7", "dimensionId2", 1);
        EvaluationResult dimension2level1question2 = createEvaluationResult("4", "8", "dimensionId2", 1);
        EvaluationResult dimension2level2question1 = createEvaluationResult("5", "9", "dimensionId2", 1);
        EvaluationResult dimension2level2question2 = createEvaluationResult("6", "10", "dimensionId2", 0);
        EvaluationResult dimension3level1question1 = createEvaluationResult("7", "13", "dimensionId3", 1);
        EvaluationResult dimension3level1question2 = createEvaluationResult("8", "14", "dimensionId3", 1);
        EvaluationResult dimension3level2question1 = createEvaluationResult("9", "15", "dimensionId3", 1);
        EvaluationResult dimension3level2question2 = createEvaluationResult("10", "16", "dimensionId3", 1);
        EvaluationResult dimension3level3question1 = createEvaluationResult("11", "17", "dimensionId3", 1);
        EvaluationResult dimension3level3question2 = createEvaluationResult("12", "18", "dimensionId3", 1);

        return List.of(dimension1level1question1,
                dimension1level1question2,
                dimension2level1question1,
                dimension2level1question2,
                dimension2level2question1,
                dimension2level2question2,
                dimension3level1question1,
                dimension3level1question2,
                dimension3level2question1,
                dimension3level2question2,
                dimension3level3question1,
                dimension3level3question2);
    }

    List<EvaluationResultDTO> createEvaluationResultsDTO(){
        EvaluationResultDTO dimension1level1question1 = createEvaluationResultDTO("1", "1", "dimensionId1", 1);
        EvaluationResultDTO dimension1level1question2 = createEvaluationResultDTO("2", "2", "dimensionId1", 0);
        EvaluationResultDTO dimension2level1question1 = createEvaluationResultDTO("3", "7", "dimensionId2", 1);
        EvaluationResultDTO dimension2level1question2 = createEvaluationResultDTO("4", "8", "dimensionId2", 1);
        EvaluationResultDTO dimension2level2question1 = createEvaluationResultDTO("5", "9", "dimensionId2", 1);
        EvaluationResultDTO dimension2level2question2 = createEvaluationResultDTO("6", "10", "dimensionId2", 0);
        EvaluationResultDTO dimension3level1question1 = createEvaluationResultDTO("7", "13", "dimensionId3", 1);
        EvaluationResultDTO dimension3level1question2 = createEvaluationResultDTO("8", "14", "dimensionId3", 1);
        EvaluationResultDTO dimension3level2question1 = createEvaluationResultDTO("9", "15", "dimensionId3", 1);
        EvaluationResultDTO dimension3level2question2 = createEvaluationResultDTO("10", "16", "dimensionId3", 1);
        EvaluationResultDTO dimension3level3question1 = createEvaluationResultDTO("11", "17", "dimensionId3", 1);
        EvaluationResultDTO dimension3level3question2 = createEvaluationResultDTO("12", "18", "dimensionId3", 1);

        return List.of(dimension1level1question1,
                dimension1level1question2,
                dimension2level1question1,
                dimension2level1question2,
                dimension2level2question1,
                dimension2level2question2,
                dimension3level1question1,
                dimension3level1question2,
                dimension3level2question1,
                dimension3level2question2,
                dimension3level3question1,
                dimension3level3question2);
    }

    Evaluation createCompletedEvaluation(){
        return Evaluation.builder()
                .evaluationId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                //responde una del primer nivel (1)
                //responde todas las del primer nivel y una del segundo (2,3,4)
                //responde todas las preguntas (5,6,7,8,9,10)
                .questionResults(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
                .dimensionResults(List.of(
                        DimensionResult.builder()
                            .dimensionId("dimensionId1")
                            .dimensionName("Dimension")
                            .levelName("Level1")
                            .levelId("levelId1")
                            .levelValue(1)
                            .build(),
                        DimensionResult.builder()
                            .dimensionId("dimensionId2")
                            .dimensionName("Dimension2")
                            .levelId("levelId4")
                            .levelValue(1)
                            .levelName("Level1")
                            .build(),
                        DimensionResult.builder()
                            .dimensionId("dimensionId3")
                            .dimensionName("Dimension3")
                            .levelValue(2)
                            .levelId("levelId8")
                            .levelName("Level2")
                            .build()
                        )
                )
                .completed(true)
                .build();
    }

    Dimension createDimension(){
        return Dimension.builder()
                .dimensionId("dimensionId1")
                .name("Dimension")
                .levels(List.of(
                        Level.builder()
                                .levelId("levelId1")
                                .name("Level1")
                                .value(1)
                                .questions(List.of("1", "2"))
                                .build(),
                        Level.builder()
                                .levelId("levelId2")
                                .name("Level2")
                                .value(2)
                                .questions(List.of("3", "4"))
                                .build(),
                        Level.builder()
                                .levelId("levelId3")
                                .name("Level3")
                                .value(3)
                                .questions(List.of("5", "6"))
                                .build()
                ))
                .build();
    }

    Dimension createDimension2(){
        return Dimension.builder()
                .dimensionId("dimensionId2")
                .name("Dimension2")
                .levels(List.of(
                        Level.builder()
                                .levelId("levelId4")
                                .name("Level1")
                                .value(1)
                                .questions(List.of("7", "8"))
                                .build(),
                        Level.builder()
                                .levelId("levelId5")
                                .name("Level2")
                                .value(2)
                                .questions(List.of("9", "10"))
                                .build(),
                        Level.builder()
                                .levelId("levelId6")
                                .name("Level3")
                                .value(3)
                                .questions(List.of("11", "12"))
                                .build()
                ))
                .build();
    }

    Dimension createDimension3(){
        return Dimension.builder()
                .dimensionId("dimensionId3")
                .name("Dimension3")
                .levels(List.of(
                        Level.builder()
                                .levelId("levelId7")
                                .name("Level1")
                                .value(1)
                                .questions(List.of("13", "14"))
                                .build(),
                        Level.builder()
                                .levelId("levelId8")
                                .name("Level2")
                                .value(2)
                                .questions(List.of("15","16"))
                                .build(),
                        Level.builder()
                                .levelId("levelId9")
                                .name("Level3")
                                .value(3)
                                .questions(List.of("17", "18"))
                                .build()
                ))
                .build();
    }

    Question createQuestion(String order, String dimensionId){
        return Question.builder()
                .questionId(order)
                .question("Question"+order)
                .scorePositive(1)
                .dimensionId(dimensionId)
                .options(List.of(
                        Option.builder()
                                .optionId("optionId1")
                                .description("Option1")
                                .value(1)
                                .build(),
                        Option.builder()
                                .optionId("optionId2")
                                .description("Option2")
                                .value(0)
                                .build()
                ))
                .build();
    }

    EvaluationResult createEvaluationResult(String order, String question, String dimensionId, int option){
        return EvaluationResult.builder()
                .evaluationResultId("evaluationResultId"+order)
                .questionId(question)
                .optionId(option == 1 ? "optionId1" : "optionId2")
                .dimensionId(dimensionId)
                .build();
    }

    EvaluationResultDTO createEvaluationResultDTO(String order, String question, String dimensionId, int option){
        return EvaluationResultDTO.builder()
                .questionId(question)
                .optionId(option == 1 ? "optionId1" : "optionId2")
                .marked(false)
                .build();
    }




}
