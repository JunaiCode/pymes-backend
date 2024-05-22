package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ActionPlanOutDTO {

    private String dimension;
    private String dimensionId;
    private String description;
    private List<RecomActionPlanOut> recommendations;
}
