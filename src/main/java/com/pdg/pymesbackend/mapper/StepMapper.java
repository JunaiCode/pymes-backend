package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.StepDTO;
import com.pdg.pymesbackend.model.Step;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StepMapper {
    Step fromDTO(StepDTO step);
}
