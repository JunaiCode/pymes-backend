package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.mapper.RecommendationMapper;
import com.pdg.pymesbackend.model.Recommendation;
import com.pdg.pymesbackend.repository.RecommendationRepository;
import com.pdg.pymesbackend.service.modules.RecommendationService;

public class RecommendationServiceImpl implements RecommendationService {

    private RecommendationRepository recommendationRepository;
    private RecommendationMapper recommendationMapper;
    @Override
    public Recommendation createRecommendation(RecommendationDTO recommendation) {
        return recommendationRepository.save(recommendationMapper.fromDTO(recommendation));
    }
}
