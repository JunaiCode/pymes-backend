package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/evaluation")
public interface EvaluationAPI {
    @PostMapping("/add/{companyId}")
    Evaluation createEvaluation(@PathVariable String companyId);

    @PostMapping("/finish/{evaluationId}")
    void finishEvaluation(@PathVariable String evaluationId);

    @PostMapping("/{evaluationId}/addAnswers")
    List<EvaluationResult> addAnswers(@RequestBody @Valid List<EvaluationResultDTO> evaluationResultDTO, @PathVariable String evaluationId);

    @GetMapping("/{evaluationId}")
    Evaluation getEvaluation(@PathVariable String evaluationId);

    @GetMapping("/company/{companyId}/results")
    Map<String, List<QuestionOutDTO>> checkUncompletedEvaluation(@PathVariable String companyId);
}
