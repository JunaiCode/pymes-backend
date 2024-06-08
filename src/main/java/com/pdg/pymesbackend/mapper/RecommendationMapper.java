package com.pdg.pymesbackend.mapper;

import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.model.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    Recommendation fromDTO(RecommendationDTO recommendation);
}
