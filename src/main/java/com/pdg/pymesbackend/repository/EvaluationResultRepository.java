package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvaluationResultRepository extends MongoRepository<EvaluationResult, String>{

}
