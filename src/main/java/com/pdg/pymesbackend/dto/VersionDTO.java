package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionDTO {

        private String name;
        private List<LevelDTO> levels;
        private List<DimensionDTO> dimensions;
}
