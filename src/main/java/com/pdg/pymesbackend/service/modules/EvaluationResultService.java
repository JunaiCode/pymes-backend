package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.model.EvaluationResult;

import java.util.List;

public interface EvaluationResultService {

    EvaluationResult save(EvaluationResultDTO evaluationResultDTO);

    EvaluationResult findById(String id);

    List<EvaluationResult> saveAll(List<EvaluationResultDTO> evaluationResults);

    void deleteAllById(List<String> evaluationResultId);

    List<EvaluationResult> getEvaluationResults(List<String> evaluationResultId);
}
