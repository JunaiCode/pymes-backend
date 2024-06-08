package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag fromDTO(TagDTO tag);
    TagDTO toDTO(Tag tag);
}
