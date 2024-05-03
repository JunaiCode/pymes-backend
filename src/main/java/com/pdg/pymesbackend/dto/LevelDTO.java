package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class LevelDTO {
    @NotBlank
    private String name;
    private String description;
    @Min(0)
    private int scoreMin;
    @Max(100)
    private int scoreMax;
}
