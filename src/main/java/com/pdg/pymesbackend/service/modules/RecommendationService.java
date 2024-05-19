package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.model.Recommendation;

public interface RecommendationService {

    Recommendation createRecommendation(RecommendationDTO recommendation);

}
