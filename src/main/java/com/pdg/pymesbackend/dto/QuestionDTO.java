package com.pdg.pymesbackend.dto;

import com.pdg.pymesbackend.enums.QuestionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class QuestionDTO {
    @NotBlank
    private String default_;
    @NotBlank
    private String questionType;
    private Double weight;
    @Min(0)
    private int scorePositive;
    @NotEmpty
    private List<OptionDTO> options;
    @NotBlank
    private String versionId;
    @NotBlank
    private String dimensionId;
    @NotBlank
    private String tagId;
}
