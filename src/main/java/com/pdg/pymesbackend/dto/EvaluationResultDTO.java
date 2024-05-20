package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EvaluationResultDTO {
    private String dimensionId;
    private String questionId;
    private String optionId;
    private boolean marked;
}
