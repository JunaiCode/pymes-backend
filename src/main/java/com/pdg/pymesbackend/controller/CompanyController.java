package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.CompanyAPI;
import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.service.modules.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class CompanyController implements CompanyAPI {

    private final CompanyService companyService;
    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        return companyService.save(companyDTO);
    }
}
