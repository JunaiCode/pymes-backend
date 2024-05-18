package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    private String evaluationId;
    private LocalDateTime date;
    private List<EvaluationResult> questionResults;
    private List<DimensionResult> dimensionResults;
    private boolean completed;
    private String actionPlanId;
}
