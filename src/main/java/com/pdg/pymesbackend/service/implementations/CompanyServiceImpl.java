package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.mapper.CompanyMapper;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;
    @Override
    public Company save(CompanyDTO companyDTO) {
        Company company = companyMapper.fromCompanyDTO(companyDTO);
        return companyRepository.save(company);
    }
}
