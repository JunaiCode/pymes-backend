package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.ActionPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionPlanRepository extends MongoRepository<ActionPlan, String> {
}
