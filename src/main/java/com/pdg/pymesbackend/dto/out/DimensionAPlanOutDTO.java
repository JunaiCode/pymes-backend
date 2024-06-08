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
public class DimensionAPlanOutDTO {

    private String dimension;
    private String dimensionId;
    private String description;
    private List<RecomActionPlanOut> recommendations;
}
