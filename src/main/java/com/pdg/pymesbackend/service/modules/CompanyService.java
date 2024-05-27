package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;

public interface CompanyService {
    Company save(CompanyDTO companyDTO);

    Company getCompanyById(String companyId);

    OnGoingEvaluationOutDTO checkUncompletedEvaluation(String companyId);

    Evaluation startEvaluation(String companyId);

    CompanyOutDTO getCompanyOut(String companyId);
}
