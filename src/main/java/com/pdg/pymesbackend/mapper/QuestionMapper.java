package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question fromDTO(QuestionDTO question);
}
