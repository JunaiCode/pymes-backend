package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.modules.EvaluationResultService;
import com.pdg.pymesbackend.service.modules.QuestionService;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.modules.implementations.EvaluationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
    }
}
