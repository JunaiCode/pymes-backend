package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private String companyId;
    private String name;
    private String address;
    private LocalDateTime creationDate;
    private String email;
    private int employees;
    private CompanyType companyType;
    private String[] evaluations;
    private String economicSectorId;

}
