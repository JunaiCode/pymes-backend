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
public class ActionPlan {

    @Id
    private String actionPlanId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Recommendation[] recommendations;
    private Output[] outputs;

}
