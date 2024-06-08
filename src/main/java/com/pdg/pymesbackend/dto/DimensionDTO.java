package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DimensionDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private List<LevelDTO> levels;
}
