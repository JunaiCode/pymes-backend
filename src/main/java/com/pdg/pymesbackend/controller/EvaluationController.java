package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.EvaluationAPI;
import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class EvaluationController implements EvaluationAPI {
    private final EvaluationService evaluationService;
    @Override
    public Evaluation createEvaluation(EvaluationDTO evaluationDTO) {
        return evaluationService.save(evaluationDTO);
    }
}
