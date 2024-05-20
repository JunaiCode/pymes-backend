package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.CompanyMapper;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.service.modules.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;
    @Override
    public Company save(CompanyDTO companyDTO) {
        Company company = companyMapper.fromCompanyDTO(companyDTO);
        company.setCompanyId(UUID.randomUUID().toString());
        company.setEvaluations(List.of());
        return companyRepository.save(company);
    }

    public Company addEvaluationToCompany(String companyId, String evaluationId) {
        Company company = getCompanyById(companyId);
        //pendiente validar si la evaluacion existe
        company.getEvaluations().add(evaluationId);
        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(String companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new PymeException(PymeExceptionType.COMPANY_NOT_FOUND));
    }
}
