package com.pdg.pymesbackend.dto;

import com.pdg.pymesbackend.enums.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionDTO {
    @NotBlank
    private String question;
    private Double weight;
    @Min(0)
    private int scorePositive;
    @NotEmpty
    private List<OptionDTO> options;
    @NotNull
    private RecommendationDTO recommendation;
    @NotBlank
    private String versionId;
    @NotBlank
    private String dimensionId;
    @NotBlank
    private String tagId;
    @NotBlank
    private String companyTypeId;
}
