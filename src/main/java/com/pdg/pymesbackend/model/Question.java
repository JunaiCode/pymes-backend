package com.pdg.pymesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private String questionId;
    @Field("default")
    private String default_;
    private QuestionType questionType;
    private Double weight;
    private int scorePositive;
    private Option[] options;
    private String[] recommendations;
    private String versionId;
    private String dimensionId;
    private String tagId;

}
