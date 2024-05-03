package com.pdg.pymesbackend.model;

import com.pdg.pymesbackend.model.enums.QuestionType;
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
    @Field("default")
    private String default_;
    private QuestionType questionType;
    private Double weight;
    private int scorePositive;
    private List<Option> options;
    private List<String> recommendations;
    private String versionId;
    private String dimensionId;
    private String tagId;

}
