package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.model.EvaluationResult;

import java.util.List;

public interface EvaluationResultService {

    EvaluationResult save(EvaluationResultDTO evaluationResultDTO);

    List<EvaluationResult> getEvaluationResults(List<String> evaluationResultId);
}
