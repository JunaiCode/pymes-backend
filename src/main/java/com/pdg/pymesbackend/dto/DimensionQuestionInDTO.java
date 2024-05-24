package com.pdg.pymesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class DimensionQuestionInDTO {
        //@NotBlank
        private String dimensionId;
        //@NotBlank
        private String levelId;
        //@NotBlank
        private String versionId;
        //@NotBlank
        private String companyTypeId;
}
