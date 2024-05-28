package com.pdg.pymesbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
@AllArgsConstructor
public class Company {

    @Id
    private String companyId;
    private String name;
    private String address;
    private String cityId;
    private LocalDateTime creationDate;
    private String legalRepEmail;
    private String legalRepTel;
    private String legalRep;
    private String nit;
    private int employees;
    private String tel;
    private CompanyType companyType;
    private List<String> evaluations;
    private String economicSectorId;
    private String password;
}
