package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.EvaluationAPI;
import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.EvaluationResultOutDTO;
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
    public Evaluation createEvaluation(EvaluationDTO evaluationDTO, String companyId) {

        return evaluationService.save(evaluationDTO, companyId);
    }

    @Override
    public EvaluationResult addAnswer(EvaluationResultDTO evaluationResultDTO, String evaluationId) {
        return evaluationService.addEvaluationResult(evaluationId, evaluationResultDTO);
    }

    @Override
    public Evaluation getEvaluation(String evaluationId) {
        return null;
    }

    @Override
    public Map<String, List<EvaluationResultOutDTO>> getEvaluationResults(String evaluationId) {
        return null;
    }
}
