package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.EvaluationAPI;
import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
public class EvaluationController implements EvaluationAPI {
    private final EvaluationService evaluationService;
    @Override
    public Evaluation createEvaluation(String companyId) {

        return evaluationService.save(companyId);
    }

    @Override
    public List<EvaluationResult> addAnswers(List<EvaluationResultDTO> evaluationResultDTO, String evaluationId) {
        return evaluationService.addEvaluationResults(evaluationId, evaluationResultDTO);
    }

    @Override
    public Evaluation getEvaluation(String evaluationId) {
        return null;
    }

    @Override
    public Map<String, List<QuestionOutDTO>> getEvaluationResults(String evaluationId) {
        return evaluationService.getEvaluationResults(evaluationId);
    }
}
