package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.model.Option;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper{
    Option fromDTO(OptionDTO option);
}
