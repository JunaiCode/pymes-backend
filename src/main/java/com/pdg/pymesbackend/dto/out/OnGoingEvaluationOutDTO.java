package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OnGoingEvaluationOutDTO {
    private String evaluationId;
    private List<QuestionOutDTO> questions;
}
