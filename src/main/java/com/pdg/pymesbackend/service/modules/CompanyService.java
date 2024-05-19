package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.model.Company;

public interface CompanyService {
    Company save(CompanyDTO companyDTO);
}
