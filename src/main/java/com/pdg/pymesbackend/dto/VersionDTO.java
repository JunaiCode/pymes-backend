package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionDTO {

        private String name;
        private LevelDTO[] levels;
        private DimensionDTO[] dimensions;
}
