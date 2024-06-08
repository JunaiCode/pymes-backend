package com.pdg.pymesbackend.dto.out;

import com.pdg.pymesbackend.model.CompanyType;
import com.pdg.pymesbackend.model.DimensionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class CompanyOutDTO {

    private String name;
    private int employees;
    private CompanyType companyType;
    private String economicSectorId;
    private List<DimensionResult> currentEvaluation;
    private List<EvaluationHistoryOutDTO> evaluationHistory;
}
