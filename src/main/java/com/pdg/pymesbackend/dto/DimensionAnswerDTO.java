package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DimensionAnswerDTO {

    String dimensionId;
    String levelId;
}
