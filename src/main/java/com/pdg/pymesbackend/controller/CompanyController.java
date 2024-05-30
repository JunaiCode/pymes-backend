package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.CompanyAPI;
import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.CompanyInfoDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.service.modules.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class CompanyController implements CompanyAPI {

    private final CompanyService companyService;
    /*@Override
    public Company createCompany(CompanyDTO companyDTO) {
        return companyService.save(companyDTO);
    }*/

    @Override
    public CompanyOutDTO getCompany(String id) {
        return companyService.getCompanyOut(id);
    }

    @Override
    public CompanyInfoDTO getInfoCompany(String id) {
        return  companyService.getCompanyInfo(id);
    }

    @Override
    public Company setInfoCompany(String id, CompanyInfoDTO company) {
        return companyService.setCompanyInfo(id,company);
    }

    @Override
    public OnGoingEvaluationOutDTO checkUncompletedEvaluation(String companyId) {
        return companyService.checkUncompletedEvaluation(companyId);
    }

    @Override
    public Evaluation startEvaluation(String companyId) {
        return companyService.startEvaluation(companyId);
    }

    @Override
    public ActionPlanOutDTO getActualActionPlan(String companyId) {
        return companyService.getActualActionPlan(companyId);
    }
}
