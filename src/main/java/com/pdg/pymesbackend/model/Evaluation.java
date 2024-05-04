package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    private String evaluationId;
    private LocalDateTime date;
    private List<EvaluationResult> results;
    private String actionPlanId;
}
