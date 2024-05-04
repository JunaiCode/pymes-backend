package com.pdg.pymesbackend.dto;

import com.pdg.pymesbackend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private String questionId;
    private String default_;
    private QuestionType questionType;
    private Double weight;
    private int scorePositive;
    private String options;
    private String recommendations;
    private String versionId;
    private String dimensionId;
    private String tagId;
}
