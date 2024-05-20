package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.EvaluationResultOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/evaluation")
public interface EvaluationAPI {
    @PostMapping("/add/{companyId}")
    Evaluation createEvaluation(@RequestBody @Valid EvaluationDTO evaluationDTO, @PathVariable String companyId);

    @PostMapping("/{evaluationId}/addAnswer")
    EvaluationResult addAnswer(@RequestBody @Valid EvaluationResultDTO evaluationResultDTO, @PathVariable String evaluationId);

    @GetMapping("/{evaluationId}")
    Evaluation getEvaluation(@PathVariable String evaluationId);

    @GetMapping("/{evaluationId}/results")
    Map<String, List<EvaluationResultOutDTO>> getEvaluationResults(@PathVariable String evaluationId);
}
