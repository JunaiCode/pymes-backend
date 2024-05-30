package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResult {

    @Id
    private String evaluationResultId;
    private String dimensionId;
    private String questionId;
    private String optionId;
    private boolean marked;
}
