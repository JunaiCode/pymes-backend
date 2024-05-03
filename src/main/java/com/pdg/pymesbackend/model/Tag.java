package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    private String tagId;
    private String name;
    private String description;
    private String dimensionId;
}
