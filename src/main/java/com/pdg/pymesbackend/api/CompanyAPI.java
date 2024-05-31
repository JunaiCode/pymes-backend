package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.CompanyInfoDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/company")
public interface CompanyAPI {

   /* @PostMapping("/add")
    Company createCompany(@RequestBody @Valid CompanyDTO companyDTO);*/

    @GetMapping("/get/{id}")
    CompanyOutDTO getCompany(@PathVariable String id);

    @GetMapping("/get/info/{id}")
    CompanyInfoDTO getInfoCompany(@PathVariable String id);

    @PostMapping("/set/info/{id}")
    Company setInfoCompany(@PathVariable String id , @RequestBody CompanyInfoDTO company);

    @GetMapping("/{companyId}/results")
    OnGoingEvaluationOutDTO checkUncompletedEvaluation(@PathVariable String companyId);

    @PostMapping("/{companyId}/evaluation")
    Evaluation startEvaluation(@PathVariable String companyId);

    @GetMapping("/{id}/actionPlan/actual")
    ActionPlanOutDTO getActualActionPlan(@PathVariable("id") String companyId);
}
