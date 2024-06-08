package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginOutDTO {
    private String email;
    private String role;
    private String company;
    private String id;
    private String actualVersion;
    private String companyType;
}
