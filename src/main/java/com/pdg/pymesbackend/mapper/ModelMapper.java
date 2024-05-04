package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    Model fromCreateDTO(ModelDTO model);

}
