package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResult {

    @Id
    private String evaluationResultId;
    private String dimensionId;
    private String questionId;
    private int score;
}
