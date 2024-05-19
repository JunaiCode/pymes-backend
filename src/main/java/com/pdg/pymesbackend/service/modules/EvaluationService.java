package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationInDTO;
import com.pdg.pymesbackend.dto.QuestionAnswerDTO;
import com.pdg.pymesbackend.model.Evaluation;

import java.util.List;

public interface EvaluationService {
    Evaluation save(EvaluationDTO evaluationDTO);

    void makeEvaluation(EvaluationInDTO evaluationInDTO);
}
