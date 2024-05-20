package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private String questionId;
    private String question;
    private Double weight;
    private int scorePositive;
    private List<Option> options;
    private Recommendation recommendation;
    private String dimensionId;
    private String tagId;
    private String companyTypeId;

}
