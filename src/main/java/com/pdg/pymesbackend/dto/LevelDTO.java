package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Min(0)
    private Integer value;
    private List<String> questions;

}
