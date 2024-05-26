package com.pdg.pymesbackend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ActionPlanOutDTO {

    private String actionPlanId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<DimensionAPlanOutDTO> info;
}
