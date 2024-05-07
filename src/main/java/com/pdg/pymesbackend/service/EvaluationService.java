package com.pdg.pymesbackend.service;


import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.model.Evaluation;

public interface EvaluationService {
    Evaluation save(EvaluationDTO evaluationDTO);
}
