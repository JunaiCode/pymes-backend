package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CompanyInfoDTO {
    private String address;
    private String legalRep;
    private String legalRepEmail;
    private String legalRepTel;
    private String name;
    private String nit;
    private Integer numberEmployees;
    private String tel;
}
