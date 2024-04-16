package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
