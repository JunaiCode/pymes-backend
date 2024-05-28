package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginOutDTO {
    private String email;
    private String role;
    private String company;
    private String id;
    private String actualVersion;
}
