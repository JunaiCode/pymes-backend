package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.RecommendationMapper;
import com.pdg.pymesbackend.model.Recommendation;
import com.pdg.pymesbackend.repository.RecommendationRepository;
import com.pdg.pymesbackend.service.modules.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private RecommendationRepository recommendationRepository;
    private RecommendationMapper recommendationMapper;
    @Override
    public Recommendation createRecommendation(RecommendationDTO recommendation) {
        return recommendationRepository.save(recommendationMapper.fromDTO(recommendation));
    }

    @Override
    public Recommendation getRecommendationById(String recommendationId) {
        return recommendationRepository.findById(recommendationId).orElseThrow(()-> new PymeException(PymeExceptionType.RECOMMENDATION_NOT_FOUND));
    }
}
