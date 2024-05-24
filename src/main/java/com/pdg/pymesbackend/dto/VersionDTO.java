package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class VersionDTO {
        @NotBlank
        private String name;
        private List<DimensionDTO> dimensions;
}
