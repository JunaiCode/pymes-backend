package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvaluationRepository extends MongoRepository<Evaluation, String> {
}
