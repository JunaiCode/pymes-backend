package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;

import java.util.List;

public interface EvaluationService {
    Evaluation save(String companyId);

    void finishEvaluation(String evaluationId, String versionId);

    OnGoingEvaluationOutDTO checkUncompletedEvaluation(Company companyId);

    Evaluation getRecentCompletedEvaluation(List<String> evaluations);

    Evaluation getEvaluationById(String evaluationId);

    List<Evaluation> getCompletedEvaluationsByIds(List<String> evaluationsIds);

    List<EvaluationResult> addEvaluationResults(String evaluationId, List<EvaluationResultDTO> answers);

    void addActionPlanToEvaluation(String evaluationId, String actionPlanId);
}
