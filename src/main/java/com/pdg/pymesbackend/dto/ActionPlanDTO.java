package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ActionPlanDTO {

    LocalDateTime startDate;
}
