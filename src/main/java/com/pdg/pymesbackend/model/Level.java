package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
public class Level {

    @Id
    private String levelId;
    private String name;
    private Integer value;
    private String description;
    private List<String> questions;


}
