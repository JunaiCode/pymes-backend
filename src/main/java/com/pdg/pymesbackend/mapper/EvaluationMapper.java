package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.model.Evaluation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    Evaluation fromEvaluationDTO(EvaluationDTO evaluationDTO);
}
