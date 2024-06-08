package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.EvaluationResultMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.EvaluationResultRepository;
import com.pdg.pymesbackend.service.modules.QuestionService;
import com.pdg.pymesbackend.service.modules.implementations.EvaluationResultServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationResultServiceTest {

    @Mock
    private EvaluationResultRepository evaluationResultRepository;
    @Spy
    private EvaluationResultMapper evaluationResultMapper;
    @Mock
    private QuestionService questionService;
    @InjectMocks
    private EvaluationResultServiceImpl evaluationResultService;

    @Test
    void testSave() {
        when(evaluationResultRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(evaluationResultMapper.fromDTO(any())).thenReturn(createEvaluationResult());
        evaluationResultService.save(createEvaluationResultDTO());
        ArgumentCaptor<EvaluationResult> captor = ArgumentCaptor.forClass(EvaluationResult.class);
        verify(evaluationResultRepository, times(1)).save(captor.capture());
        assertEquals("1", captor.getValue().getQuestionId());
        assertEquals("1", captor.getValue().getOptionId());

    }

    @Test
    void testFindById() {
        when(evaluationResultRepository.findById("1")).thenReturn(Optional.of(createEvaluationResult()));
        EvaluationResult result = evaluationResultService.findById("1");
        assertEquals("1", result.getQuestionId());
        assertEquals("1", result.getOptionId());
    }

    @Test
    void testFindByIdNotFound() {
        when(evaluationResultRepository.findById("1")).thenReturn(Optional.empty());
        try {
            evaluationResultService.findById("1");
        } catch (Exception e) {
            assertEquals("Evaluation result not found", e.getMessage());
        }
    }

    @Test
    void testSaveAll(){
        when(evaluationResultRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(evaluationResultMapper.fromDTO(any())).thenReturn(createEvaluationResult());
        when(questionService.getQuestion("1")).thenReturn(createQuestion());
        evaluationResultService.saveAll(List.of(createEvaluationResultDTO(), createEvaluationResultDTO()));
        ArgumentCaptor<List<EvaluationResult>> captor = ArgumentCaptor.forClass(List.class);
        verify(evaluationResultRepository, times(1)).saveAll(captor.capture());
        assertEquals(2, captor.getValue().size());
        assertEquals("1", captor.getValue().get(0).getQuestionId());
        assertEquals("1", captor.getValue().get(0).getOptionId());
    }

    @Test
    void testSaveAllQuestionNotFound(){
        when(questionService.getQuestion("1")).thenThrow(new PymeException(PymeExceptionType.QUESTION_NOT_FOUND));
        when(evaluationResultMapper.fromDTO(any())).thenReturn(createEvaluationResult());
        try {
            evaluationResultService.saveAll(List.of(createEvaluationResultDTO(), createEvaluationResultDTO()));
        } catch (Exception e) {
            assertEquals("Question not found", e.getMessage());
        }
    }

    @Test
    void testGetEvaluationResults(){
        when(evaluationResultRepository.findAllById(List.of("1"))).thenReturn(List.of(createEvaluationResult()));
        List<EvaluationResult> results = evaluationResultService.getEvaluationResults(List.of("1"));
        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getQuestionId());
        assertEquals("1", results.get(0).getOptionId());
    }

    @Test
    void testDeleteAllById(){
        evaluationResultService.deleteAllById(List.of("1"));
        verify(evaluationResultRepository, times(1)).deleteAllByEvaluationResultIdIn(List.of("1"));
    }

    @Test
    void testDeleteById(){
        evaluationResultService.deleteById("1");
        verify(evaluationResultRepository, times(1)).deleteById("1");
    }

    EvaluationResult createEvaluationResult(){
        return EvaluationResult.builder()
                .evaluationResultId("1")
                .questionId("1")
                .optionId("1")
                .dimensionId("1")
                .marked(false)
                .build();
    }

    EvaluationResultDTO createEvaluationResultDTO(){
        return EvaluationResultDTO.builder()
                .questionId("1")
                .optionId("1")
                .questionId("1")
                .marked(false)
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
