package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;

import java.util.List;
import java.util.Map;

public interface EvaluationService {
    Evaluation save(String companyId);

    void finishEvaluation(String evaluationId);

    Map<String, List<QuestionOutDTO>> checkUncompletedEvaluation(String companyId);

    Evaluation getEvaluationById(String evaluationId);

    List<EvaluationResult> addEvaluationResults(String evaluationId, List<EvaluationResultDTO> answers);

    void addActionPlanToEvaluation(String evaluationId, String actionPlanId);
}
