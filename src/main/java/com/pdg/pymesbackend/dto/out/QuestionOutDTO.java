package com.pdg.pymesbackend.dto.out;

import com.pdg.pymesbackend.model.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class QuestionOutDTO {

    private String question;
    private String questionId;
    private List<Option> options;
    private Option answer;
    private boolean marked;

}
