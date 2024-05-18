package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationActionPlan {
    @Id
    private String recommendationActionPlanId;
    private Recommendation recommendation;
    private boolean completed;
    private LocalDateTime date;

}
