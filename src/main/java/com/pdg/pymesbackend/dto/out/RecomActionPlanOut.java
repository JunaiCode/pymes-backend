package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RecomActionPlanOut {
    private String dimensionId;
    private String recommendationId;
    private String description;
    private List<StepOutDTO> steps;
    private String tagName;
}
