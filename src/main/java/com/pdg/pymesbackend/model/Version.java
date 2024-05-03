package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
public class Version {

    @Id
    private String versionId;
    private String name;
    private List<Level> levels;
    private List<Dimension> dimensions;
}
