package com.pdg.pymesbackend.dto.out;

import com.pdg.pymesbackend.model.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class EvaluationResultOutDTO {

    private String question;
    private List<Option> options;
    private Option answer;

}
