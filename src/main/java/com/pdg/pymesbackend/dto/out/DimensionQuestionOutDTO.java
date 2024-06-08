package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DimensionQuestionOutDTO {

    private String dimensionId;
    private List<QuestionOutDTO> questions;
}
