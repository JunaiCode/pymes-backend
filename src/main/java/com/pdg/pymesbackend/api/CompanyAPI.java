package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.model.Company;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/company")
public interface CompanyAPI {

    @PostMapping("/add")
    Company createCompany(@RequestBody @Valid CompanyDTO companyDTO);
}
