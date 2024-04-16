package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DimensionMapper {

    Dimension fromCreateDTO(DimensionDTO dimension);
}
