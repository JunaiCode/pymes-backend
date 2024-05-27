package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/evaluation")
public interface EvaluationAPI {
    @PostMapping("/add/{companyId}")
    Evaluation createEvaluation(@PathVariable String companyId);

    @PostMapping("/finish/{evaluationId}/version/{versionId}")
    void finishEvaluation(@PathVariable String evaluationId, @PathVariable String versionId);

    @PostMapping("/{evaluationId}/addAnswers")
    List<EvaluationResult> addAnswers(@RequestBody @Valid List<EvaluationResultDTO> evaluationResultDTO, @PathVariable String evaluationId);

    @GetMapping("/{evaluationId}")
    Evaluation getEvaluation(@PathVariable String evaluationId);

}
