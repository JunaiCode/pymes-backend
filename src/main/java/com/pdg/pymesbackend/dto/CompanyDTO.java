package com.pdg.pymesbackend.dto;

import com.pdg.pymesbackend.model.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CompanyDTO {
    private String name;
    private String address;
    private String email;
    private int employees;
    private CompanyType companyType;
    private String economicSectorId;

}
