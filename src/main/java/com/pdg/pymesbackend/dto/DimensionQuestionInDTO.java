package com.pdg.pymesbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DimensionQuestionInDTO {
        @NotBlank
        private String dimensionId;
        @NotBlank
        private String levelId;
        @NotBlank
        private String versionId;
        @NotBlank
        private String companyTypeId;
}
