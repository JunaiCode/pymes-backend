package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@AllArgsConstructor
public class Level {

    @Id
    private String levelId;
    private String name;
    private String description;
    private int scoreMin;
    private int scoreMax;
}
