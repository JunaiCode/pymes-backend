package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.DimensionAnswerDTO;
import com.pdg.pymesbackend.model.DimensionResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DimensionResultMapper {

    DimensionResult fromAnswerDTO(DimensionAnswerDTO dimensionAnswerDTO);
}
