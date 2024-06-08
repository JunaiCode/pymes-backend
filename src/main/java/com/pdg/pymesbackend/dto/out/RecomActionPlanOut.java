package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecomActionPlanOut {
    private String dimensionId;
    private String recommendationId;
    private String description;
    private List<StepOutDTO> steps;
    private String tagName;
}
