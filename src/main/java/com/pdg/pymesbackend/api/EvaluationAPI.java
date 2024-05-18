package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.model.Evaluation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/evaluation")
public interface EvaluationAPI {
    @PostMapping("/add")
    Evaluation createEvaluation(@RequestBody @Valid EvaluationDTO evaluationDTO);
}
