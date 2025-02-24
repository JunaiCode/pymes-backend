package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepOutDTO {
    private String recommendationActionPlanId;
    private String recommendationId;
    private String description;
    private boolean checked;
}
