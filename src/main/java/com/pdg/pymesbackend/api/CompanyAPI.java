package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/company")
public interface CompanyAPI {

    @PostMapping("/add")
    Company createCompany(@RequestBody @Valid CompanyDTO companyDTO);

    @GetMapping("/get/{id}")
    CompanyOutDTO getCompany(@PathVariable String id);

    @GetMapping("/{companyId}/results")
    OnGoingEvaluationOutDTO checkUncompletedEvaluation(@PathVariable String companyId);

    @PostMapping("/{companyId}/evaluation")
    Evaluation startEvaluation(@PathVariable String companyId);
}
