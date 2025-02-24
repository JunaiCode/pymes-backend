package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StepDTO {
    @NotBlank
    private String description;
    @Min(value = 0, message = "Order must be greater than or equal to 0")
    private int order;
}

