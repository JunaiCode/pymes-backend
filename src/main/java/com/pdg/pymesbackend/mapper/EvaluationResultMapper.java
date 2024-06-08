package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.model.EvaluationResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationResultMapper {

    EvaluationResult fromDTO(EvaluationResultDTO evaluationResultDTO);
}
