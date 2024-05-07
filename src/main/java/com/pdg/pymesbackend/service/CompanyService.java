package com.pdg.pymesbackend.service;


import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.model.Company;

public interface CompanyService {
    Company save(CompanyDTO companyDTO);
}
