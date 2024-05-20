package com.pdg.pymesbackend.dto.out;

import com.pdg.pymesbackend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LevelQuestionDTO {

    private String levelId;
    private List<Question> questions;
}
