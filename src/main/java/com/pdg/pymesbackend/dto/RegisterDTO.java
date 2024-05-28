package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterDTO {
    private String address;
    private Integer city;
    private Integer economicSector;
    private Integer expectations;
    private String legalRep;
    private String legalRepEmail;
    private String legalRepTel;
    private String name;
    private String nit;
    private Integer numberEmployees;
    private String opsYears;
    private String password;
    private Integer specificNeeds;
    private String tel;
    private Boolean termsAndConditions;
    private Integer type;
}
