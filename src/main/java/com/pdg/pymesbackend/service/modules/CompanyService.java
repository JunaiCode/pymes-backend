package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.CompanyInfoDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;

public interface CompanyService {
    //Company save(CompanyDTO companyDTO);

    Company save(RegisterDTO registerDTO);

    Company getCompanyById(String companyId);

    OnGoingEvaluationOutDTO checkUncompletedEvaluation(String companyId);

    ActionPlanOutDTO getActualActionPlan(String companyId);

    Evaluation startEvaluation(String companyId);

    CompanyOutDTO getCompanyOut(String companyId);

    CompanyInfoDTO getCompanyInfo(String companyId);

    Company getCompanyByEmail(String email);

    Company setCompanyInfo(String companyId, CompanyInfoDTO companyInfo);
}
