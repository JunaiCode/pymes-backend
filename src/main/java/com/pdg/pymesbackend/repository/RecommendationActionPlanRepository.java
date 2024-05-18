package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.RecommendationActionPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationActionPlanRepository extends MongoRepository<RecommendationActionPlan, String> {

}
