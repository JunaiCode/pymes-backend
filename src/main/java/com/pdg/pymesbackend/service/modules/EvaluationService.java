package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationInDTO;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.QuestionAnswerDTO;
import com.pdg.pymesbackend.dto.out.EvaluationResultOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;

import java.util.List;
import java.util.Map;

public interface EvaluationService {
    Evaluation save(EvaluationDTO evaluationDTO, String companyId);

    Map<String, List<EvaluationResultOutDTO>> getEvaluationResults(String evaluationId);

    EvaluationResult addEvaluationResult(String evaluationId, EvaluationResultDTO answer);

    void makeEvaluation(EvaluationInDTO evaluationInDTO);
}
