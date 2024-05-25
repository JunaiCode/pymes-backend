package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RecommendationDTO {
    @NotBlank
    private String description;
    @NotEmpty
    private List<StepDTO> steps;
    @NotBlank
    private String title;

}
