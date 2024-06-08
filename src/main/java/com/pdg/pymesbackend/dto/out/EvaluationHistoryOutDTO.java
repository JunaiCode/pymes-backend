package com.pdg.pymesbackend.dto.out;

import com.pdg.pymesbackend.model.DimensionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EvaluationHistoryOutDTO {

    private LocalDateTime date;
    private List<DimensionResult> dimensionResults;
}
