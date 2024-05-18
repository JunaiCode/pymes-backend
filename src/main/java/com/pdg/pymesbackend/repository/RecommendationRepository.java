package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
}
