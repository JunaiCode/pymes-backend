package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {

    Level fromLevelDTO (LevelDTO level);
}
