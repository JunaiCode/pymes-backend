package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Version;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface VersionMapper {
    Version fromDTO(VersionDTO version);

}
