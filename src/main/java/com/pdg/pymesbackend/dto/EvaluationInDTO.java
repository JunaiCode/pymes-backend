package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EvaluationInDTO {
    private List<DimensionAnswerDTO> dimensionResults;
    private List<QuestionAnswerDTO> answers;
}
