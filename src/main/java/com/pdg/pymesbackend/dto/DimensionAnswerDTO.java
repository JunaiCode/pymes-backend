package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DimensionAnswerDTO {

    String dimensionId;
    String levelId;
}
